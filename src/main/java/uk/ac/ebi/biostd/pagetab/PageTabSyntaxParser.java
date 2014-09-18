package uk.ac.ebi.biostd.pagetab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.Tag;
import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.trfactory.FileTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.LinkTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.SectionTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.SubmissionTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.context.BlockContext;
import uk.ac.ebi.biostd.pagetab.context.BlockContext.BlockType;
import uk.ac.ebi.biostd.pagetab.context.FileContext;
import uk.ac.ebi.biostd.pagetab.context.LinkContext;
import uk.ac.ebi.biostd.pagetab.context.SubmissionContext;
import uk.ac.ebi.biostd.pagetab.context.VoidContext;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;
import uk.ac.ebi.biostd.util.StringUtils;

public class PageTabSyntaxParser
{
 public static final String HorizontalBlockPrefix = "-";
 public static final String VerticalBlockPrefix = "|";

 public static final String TagSeparatorRX = "[,;]";
 public static final String ClassifierSeparatorRX = ":";
 public static final String ValueTagSeparatorRX = "=";
 
 public static final String SubmissionKeyword = "Submission";
 public static final String FileKeyword = "File";
 public static final String LinkKeyword = "Link";
 
 private final TagResolver tagRslv;
 
 public PageTabSyntaxParser( TagResolver tr )
 {
  tagRslv = tr;
 }


 interface BlockSupplier
 {
//  List<String> getHeaderLine();

//  int getRecNum();
//  boolean isHorizontal();

  List<CellValue> getRecord(List<CellValue> parts);
 
  int getOrder( CellValue cv );
 }
 
 class HorizontalBlockSupplier implements BlockSupplier
 {
  private final List<String> firstLine;
  private final SpreadsheetReader reader;
  
  HorizontalBlockSupplier(SpreadsheetReader r, List<String> fstLine)
  {
   reader = r;
   
   firstLine = new ArrayList<String>( fstLine.size() );
   
   for( String s : fstLine )
    firstLine.add( s );
  }
  

  @Override
  public int getOrder(CellValue cv)
  {
   return cv.getRow();
  }

  @Override
  public List<CellValue> getRecord(List<CellValue> parts)
  {
   List<String> line = null;
   
   if( firstLine.size() == 0 )
   {
    line = reader.readRow(firstLine);
   
    if( line == null )
     return null;
    
    if( isEmptyLine(line) )
     return null;
   }
   
   if( parts == null )
    parts = new ArrayList<CellValue>( firstLine.size() );
   else
    parts.clear();
    
   int ln = reader.getLineNumber();
   
   int col=0;  
   for( String s : firstLine )
   {
    col++;
    
    CellValue cv = new CellValue(s);
    
    cv.setRow(ln);
    cv.setCol(col);
    
    parts.add( cv );
   }
   
   firstLine.clear();
     
   return parts;
  }
  
 }
 
 class VerticalBlockSupplier implements BlockSupplier
 {
  private int ptr = 0;
  private final List<List<String>> lines = new ArrayList<List<String>>( 50 );
  private int maxDim = 0;
  private final int firstLineNum;
 
  VerticalBlockSupplier(SpreadsheetReader reader, List<String> fstLine)
  {
   firstLineNum = reader.getLineNumber();
   
   List<String> line = new ArrayList<String>( fstLine.size() );
   
   for( String s : fstLine )
    line.add(s);

   maxDim = lineSize(line);

   lines.add(line);
   
   while( ( line = reader.readRow(null) ) != null && ! isEmptyLine(line) )
   {
    lines.add(line);
   
    int sz = lineSize(line);
    
    if( maxDim < sz )
     maxDim = sz;
   }
   
  }

  private int lineSize( List<String> line )
  {
   ListIterator<String> litr = line.listIterator(line.size());
  
   int eCnt=0;
   
   while( litr.hasPrevious() )
   {
    String pt = litr.previous();
    
    if( pt.length() > 0 )
     break;
   
    eCnt++;
   }
  
   return line.size()-eCnt;
  }
  
  @Override
  public int getOrder(CellValue cv)
  {
   return cv.getCol();
  }
  
  @Override
  public List<CellValue> getRecord(List<CellValue> line)
  {
   if( ptr >= maxDim )
    return null;
   
   if( line == null )
    line = new ArrayList<CellValue>( lines.size() );
   else
    line.clear();
   
   int row=firstLineNum-1;
   int col = ptr+1;
   
   for( List<String> l : lines )
   {
    row++;
    
    CellValue cv = new CellValue( ptr >= l.size()?"":l.get(ptr) ) ;
    
    cv.setCol(col);
    cv.setRow(row);
    
    line.add(  cv );
   }
   
   ptr++;
   
   return line;
  }
  
 }

 


 public Submission parse( String txt, ParserConfig pConf, LogNode ln ) //throws ParserException
 {
  
  List<String> parts = new ArrayList<String>(100);

  List<CellValue> cells1 = new ArrayList<CellValue>(100);
  List<CellValue> cells2 = new ArrayList<CellValue>(100);
  List<CellValue> cells3 = new ArrayList<CellValue>(100);
  List<CellValue> cells4 = new ArrayList<CellValue>(100);
  List<CellValue> cells5 = new ArrayList<CellValue>(100);

  SpreadsheetReader reader = new SpreadsheetReader(txt);
 
  BlockSupplier block;
  
  Submission subm = null;
  Map<String,Section> secMap = new HashMap<>();
//  List<SecCellCoupling> sections = new ArrayList<>();
  
  BlockContext context = new VoidContext();
  
  ln.success();
  ln.log(Level.INFO, "Processing submission");
  
  
  while( reader.readRow(parts) != null )
  {
   if( isEmptyLine(parts) )
   {
    context.setBlockType(BlockType.NONE);
    continue;
   }
   
   CellValue classRef = new CellValue( parts.get(0) );
   
 
   if( classRef.matchSubstring(HorizontalBlockPrefix, 0) )
   {
    parts.set(0, classRef.getRawValue().substring(HorizontalBlockPrefix.length()));
    block = new HorizontalBlockSupplier( reader, parts );
    
   }
   else if( classRef.matchSubstring(VerticalBlockPrefix, 0) )
   {
    parts.set(0, classRef.getRawValue().substring(VerticalBlockPrefix.length()));
    block = new VerticalBlockSupplier( reader, parts );
   }
   else
   {
    block = new VerticalBlockSupplier( reader, parts );
   }
   
   if( subm == null && ! classRef.getValue().equals(SubmissionKeyword))
    ln.log(Level.ERROR, "(R"+reader.getLineNumber()+",C1) First block should be '"+SubmissionKeyword+"'");
   
   parts.clear();
   
   cells1.clear();
   if( block.getRecord(cells1) != null )
   {
    cells2.clear();
    
    if( block.getRecord(cells2) != null )
    {
     cells3.clear();
     
     if( block.getRecord(cells3) != null )
     {
      cells4.clear();
      
      if( block.getRecord(cells4) != null )
      {
       cells5.clear();
       
       if( block.getRecord( cells5 ) != null )
       {
        int r=0;
        for( CellValue cv : cells5 )
        {
         r++;
         
         if( cv.getValue().length() != 0 )
          ln.log(Level.WARN, "(R"+r+",C5) Unexpected value. Expecting blank cell");
        }
       }
      }
      
     }
    }
   }
   
   int sz1 = cells1.size();
   int sz2 = cells2.size();
   int sz3 = cells3.size();
   int sz4 = cells4.size();
   
   
   
   if( sz1 == 0 )
    ln.log(Level.WARN, "(R"+reader.getLineNumber()+",C1) No data in block");


   CellValue cell = cells1.get(0);
   
   
   if( context.getBlockType() == BlockType.NONE )
   {
    
    if( cell.getValue().equals(SubmissionKeyword) )
    {
     LogNode sln = ln.branch("(R"+cell.getRow()+",C"+cell.getCol()+") Processing '"+SubmissionKeyword+"' block");
     
     if( subm != null )
      sln.log(Level.ERROR, "(R"+cell.getRow()+",C"+cell.getCol()+") Multiple blocks: '"+SubmissionKeyword+"' are not allowed");
     
     subm = new Submission();
     
     if( sz2 > 0 )
     {
      String acc = cells2.get(0).getValue();
      
      if( acc.length() > 0 )
       subm.setAcc( cells2.get(0).getValue() );
      else
       sln.log(Level.ERROR, "(R"+cells2.get(0).getRow()+",C"+cells2.get(0).getCol()+") Missing submission ID");
  
      subm.setAccessTags( processAccessTags(cells3,0,pConf,sln) );
      subm.setTagRefs( processTags(cells4,0,pConf,SubmissionTagRefFactory.getInstance(),sln) );

     }
     
     context = new SubmissionContext( subm );
    }
    else if( cell.getValue().equals(FileKeyword) )
    {
     LogNode fln = ln.branch("(R"+cell.getRow()+",C"+cell.getCol()+") Processing '"+FileKeyword+"' block");

     if( context.getLastSection() == null )
      fln.log(Level.ERROR, "(R"+cell.getRow()+",C"+cell.getCol()+") '"+FileKeyword+ "' block should follow any section block");
     
     FileRef fr = new FileRef();
     
     if( sz2 > 0 )
     {
      String nm = cells2.get(0).getValue();
      
      if( nm.length() > 0 )
       fr.setName( nm );
  
      fr.setAccessTags( processAccessTags(cells3,0,pConf,fln) );
      fr.setTagRefs( processTags(cells4,0,pConf,FileTagRefFactory.getInstance(),fln) );
      
      if( context.getLastSection() == null )
       context.getLastSection().addFileRef(fr);
     }

     if( fr.getName() == null )
      fln.log(Level.ERROR, "(R"+cell.getRow()+",C"+cell.getCol()+") File name missing");

     FileContext fc = new FileContext( fr );
     fc.setLastSection(context.getLastSection());
     
     context = fc;
    }    
    else if( cell.getValue().equals(LinkKeyword) )
    {
     LogNode lln = ln.branch("(R"+cell.getRow()+",C"+cell.getCol()+") Processing '"+LinkKeyword+"' block");

     if( context.getLastSection() == null )
      lln.log(Level.ERROR, "(R"+cell.getRow()+",C"+cell.getCol()+") '"+LinkKeyword+ "' block should follow any section block");

     Link l = new Link();
     
     if( sz2 > 0 )
     {
      String nm = cells2.get(0).getValue();
      
      if( nm.length() > 0 )
       l.setUrl( nm );
  
      l.setAccessTags( processAccessTags(cells3,0,pConf,lln) );
      l.setTagRefs( processTags(cells4,0,pConf,LinkTagRefFactory.getInstance(),lln) );
      
      if( context.getLastSection() == null )
       context.getLastSection().addLink(l);

     }

     if( l.getUrl() == null )
      lln.log(Level.ERROR, "(R"+cell.getRow()+",C"+cell.getCol()+") Link URL missing");
     
     LinkContext lc = new LinkContext( l );
     lc.setLastSection(context.getLastSection());
     
     context = lc;
    }
    else
    {
     LogNode sln = ln.branch("(R"+cell.getRow()+",C"+cell.getCol()+") Processing '"+cell.getValue()+"' section block");

     Section s = new Section();

     if( context.getLastSection() == null )
     {
      if( subm == null )
       sln.log(Level.ERROR, "(R"+cell.getRow()+",C"+cell.getCol()+") Section must follow '"+SubmissionKeyword+"' block or other section block");
      else
       subm.setRootSection(s);
     }
     
     s.setType(cell.getValue());
     
     if( sz2 > 0 )
     {
      cell = cells2.get(0);
      
      String acc = cell.getValue();
      
      if( acc.length() > 0 )
      {
       if( secMap.containsKey(acc) )
        sln.log(Level.ERROR, "(R"+cell.getRow()+",C"+cell.getCol()+") Section accession number '"+acc+"' is arleady used for another object");
        
       s.setAcc( acc );
      }
      
      if( sz3 > 0  )
      {
       cell = cells3.get(0);

       if( cell.getValue().length() > 0)
       {
        Section pSec = secMap.get(cell.getValue());

        if(pSec != null)
        {
         s.setParentAcc(pSec.getAcc());
         s.setParentSection(pSec);
        }
        else
         sln.log(Level.ERROR, "(R" + cell.getRow() + ",C" + cell.getCol() + ") Parent section '" + acc + "' not found");

       }
       
       
       s.setAccessTags( processAccessTags(cells4,0,pConf,sln) );
       s.setTagRefs( processTags(cells5,0,pConf,SectionTagRefFactory.getInstance(),sln) );

      }
      
      if( s.getParentSection() == null )
      {
       s.setParentSection(context.getLastSection());
       
       if( context.getLastSection() != null )
        context.getLastSection().addSection(s);
      }
     }
     
//     SecCellCoupling cpl = new SecCellCoupling(cell0,s);
//     sections.add(cpl);
     
     if( s.getAcc() != null )
      secMap.put(s.getAcc(), s );
     
     context.setLastSection( s );
    
    }
   }
   
   
   
   
   for( int i=1; i < sz1; i++ )
   {
    String nm = cells1.get(i).getValue();
    
    String val = sz2 > i?cells2.get(i).getValue():"";
   
    if( val.length() == 0 )
     val = null;

    String nameQ = sz3 > i?cells3.get(i).getValue():"";
    
    if( nameQ.length() == 0 )
     nameQ = null;
    
    String valQ = sz4 > i?cells4.get(i).getValue():"";

    if( valQ.length() == 0 )
     valQ = null;
    
    if( nm.length() > 0 )
    {
     if( val == null )
     {
      if( valQ != null )
       throw new ParserException(cells4.get(i).getRow(), cells4.get(i).getCol(), "Qualifiers of empty value are not allowed");
     }
     else
      context.addAttribute(nm, val, nameQ, valQ, processTags(cells5,i,pConf,SectionTagRefFactory.getInstance(),sln) );
    }
    else
    {
     if( val != null )
      throw new ParserException(cells2.get(i).getRow(), cells2.get(i).getCol(), "Unexpected value. Expecting blank cell");

     if( nameQ != null )
      throw new ParserException(cells3.get(i).getRow(), cells3.get(i).getCol(), "Unexpected value. Expecting blank cell");

     if( valQ != null )
      throw new ParserException(cells4.get(i).getRow(), cells4.get(i).getCol(), "Unexpected value. Expecting blank cell");
    }
    
   }
   
  }

  if( sections.size() == 0 )
   throw new ParserException(0, 0, "No sections defined");
   
  
//  if( sections.get(0).sec.getParentAcc() != null  )
//   throw new ParserException(sections.get(0).cell.getRow(), sections.get(0).cell.getCol(), "Root section can't have a parent");
//  
//  for( int i=1; i < sections.size(); i++ )
//  {
//   Section sec = sections.get(i).sec;
//   
//   if( sec.getParentSection() == null )
//   {
//    Section pSec = secMap.get( sec.getParentAcc() );
//    
//    if( pSec == null )
//     throw new ParserException(sections.get(i).cell.getRow(), sections.get(i).cell.getCol(), "No parent section with ID: "+sec.getParentAcc() );
//    
//    sec.setParentSection(pSec);
//    pSec.addSection(sec);
//   }
//    
//  }

  
  return subm;
 }

 private <T extends TagRef> List<T> processTagsX(List<CellValue> cells, int i, ParserConfig pConf, TagReferenceFactory<T> tagRefFact, LogNode ln)
 {
  CellValue cell = cells.size() > 0?cells.get(i):null;
  
  List<T> tags = null;
  
  if( cell != null && cell.getValue().length() > 0 )
  {
   if( tagRslv == null )
    ln.log(Level.WARN, "(R"+cell.getRow()+",C"+cell.getCol()+") Tag resolver is not configured. Tags will be ignored");
   else
   {
    LogNode acNode = ln.branch("Resolving tags");
    tags = resolveTags(cell, acNode, pConf.missedTagLL(), tagRefFact );
    
    acNode.success();
   }
  }
  
  return tags;
 }
 
 private void processTags(List<CellValue> cells, int i, ParserConfig pConf, BlockContext ctx, LogNode ln)
 {
  CellValue cell = cells.size() > 0?cells.get(i):null;
  
  if( cell != null && cell.getValue().length() > 0 )
  {
   if( tagRslv == null )
    ln.log(Level.WARN, "(R"+cell.getRow()+",C"+cell.getCol()+") Tag resolver is not configured. Tags will be ignored");
   else
   {
    LogNode acNode = ln.branch("Resolving tags");
    
    String[] tags = cell.getValue().split("(?<!\\\\)"+TagSeparatorRX);
    List<TagRef> res = new ArrayList<>( tags.length );
    
    for( String t : tags )
    {
     t = t.trim();
     
     if( t.length() == 0 )
      continue;
     
     int pos = t.indexOf(ValueTagSeparatorRX);
     
     String nm = null;
     String val = null;
     
     if( pos != -1 )
     {
      nm=t.substring(0,pos).trim();
      val=t.substring(pos+1).trim();
     }
     
     
     String[] clsTg = nm.split(ClassifierSeparatorRX);
     
     if( clsTg.length != 2)
     {
      acNode.log(Level.WARN, "(R"+cell.getRow()+",C"+cell.getCol()+") Invalid tag reference: '"+nm+"'");
      continue;
     }
     
     Tag tg = tagRslv.getTagByName(clsTg[0].trim(),clsTg[1].trim());

     if( val != null && val.length() > 0 )
      val = StringUtils.removeEscapes(val, "\\");
     else
      val=null;
     
     if( tg != null )
     {
      TagRef tr = context.createTagRef();
      
      tr.setTag(tg);
      tr.setParameter(val);
      
      res.add(tr);
      
      acNode.log(Level.INFO, "(R"+cell.getRow()+",C"+cell.getCol()+") Tag resolved: '"+nm+"'");
     }
     else
      acNode.log(pConf.missedTagLL(), "(R"+cell.getRow()+",C"+cell.getCol()+") Tag not resolved: '"+nm+"'");
     
    }
    
   }
  }
  
 }

 private Collection<AccessTag> processAccessTags(List<CellValue> cells, int i, ParserConfig pConf, LogNode ln)
 {
  CellValue cell = cells.size() > i?cells.get(i):null;
  
  List<AccessTag> tags = null;
  
  if( cell != null && cell.getValue().length() > 0 )
  {
   if( tagRslv == null )
    ln.log(Level.WARN, "(R"+cell.getRow()+",C"+cell.getCol()+") Tag resolver is not configured. Access tags will be ignored");
   else
   {
    LogNode acNode = ln.branch("Resolving access tags");
    tags = resolveAccessTags(cell.getValue(),acNode, pConf.missedAccessTagLL(), cell.getRow(), cell.getCol() );

    acNode.success();
   }
  }

  return tags;
 }

 private <T extends TagRef> List<T> resolveTags(CellValue cell, LogNode acNode, Level missedTagLL, TagReferenceFactory<T> tagRefFact)
 {
  String[] tags = cell.getValue().split("(?<!\\\\)"+TagSeparatorRX);
  
  List<T> res = new ArrayList<>( tags.length );
  
  for( String t : tags )
  {
   t = t.trim();
   
   if( t.length() == 0 )
    continue;
   
   int pos = t.indexOf(ValueTagSeparatorRX);
   
   String nm = null;
   String val = null;
   
   if( pos != -1 )
   {
    nm=t.substring(0,pos).trim();
    val=t.substring(pos+1).trim();
   }
   
   
   String[] clsTg = nm.split(ClassifierSeparatorRX);
   
   if( clsTg.length != 2)
   {
    acNode.log(Level.WARN, "(R"+cell.getRow()+",C"+cell.getCol()+") Invalid tag reference: '"+nm+"'");
    continue;
   }
   
   Tag tg = tagRslv.getTagByName(clsTg[0].trim(),clsTg[1].trim());

   if( val != null && val.length() > 0 )
    val = StringUtils.removeEscapes(val, "\\");
   else
    val=null;
   
   if( tg != null )
   {
    T tr = tagRefFact.createTagRef();
    
    tr.setTag(tg);
    tr.setParameter(val);
    
    res.add(tr);
    
    acNode.log(Level.INFO, "(R"+cell.getRow()+",C"+cell.getCol()+") Tag resolved: '"+nm+"'");
   }
   else
    acNode.log(missedTagLL, "(R"+cell.getRow()+",C"+cell.getCol()+") Tag not resolved: '"+nm+"'");
   
   
  }
  
  return res;
 }

 private List<AccessTag> resolveAccessTags(String value, LogNode acNode, LogNode.Level ll, int r, int c )
 {
  String[] tags = value.split(TagSeparatorRX);
  
  List<AccessTag> res = new ArrayList<>( tags.length );

  for( String t : tags )
  {
   t = t.trim();
   
   if( t.length() == 0 )
    continue;
      
   AccessTag tg = tagRslv.getAccessTagByName(t);
   
   if( tg != null )
   {
    res.add(tg);
    acNode.log(Level.INFO, "(R"+r+",C"+c+") Access tag resolved: '"+t+"'");
   }
   else
    acNode.log(ll, "(R"+r+",C"+c+") Access tag not resolved: '"+t+"'");
  }
  
  return res;
 }


 private static class SecCellCoupling
 {
  public SecCellCoupling(CellValue cell, Section sec)
  {
   super();
   this.cell = cell;
   this.sec = sec;
  }

  public CellValue cell;
  public Section sec;
 }
 
 private static boolean isEmptyLine( List<String> parts )
 {
  for(String pt : parts )
   if( pt.length() != 0 )
    return false;
  
  return true;
 }
 
 interface TagRefFact<T extends TagRef>
 {
  T createTagRef();
 }
 
}

