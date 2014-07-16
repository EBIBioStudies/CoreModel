package uk.ac.ebi.biostd.pagetab.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import uk.ac.ebi.biostd.model.Annotated;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;

public class PageTabSyntaxParser
{
 public static final String HorizontalBlockPrefix = "-";
 public static final String VerticalBlockPrefix = "|";
 
 public static final String SubmissionTag = "Submission";
 public static final String FileTag = "File";
 public static final String LinkTag = "Link";
 
 
 public PageTabSyntaxParser(SyntaxProfile sp)
 {
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
//  private List<List<String>> matrix = new ArrayList<List<String>>( 100 );
  
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

 


 public Submission parse( String txt ) throws ParserException
 {
  
  List<String> parts = new ArrayList<String>(100);
  List<CellValue> cells1 = new ArrayList<CellValue>(100);
  List<CellValue> cells2 = new ArrayList<CellValue>(100);
  List<CellValue> cells3 = new ArrayList<CellValue>(100);
  List<CellValue> cells4 = new ArrayList<CellValue>(100);

  SpreadsheetReader reader = new SpreadsheetReader(txt);
 
  BlockSupplier block;
  
  Submission subm = null;
  Map<String,Section> secMap = new HashMap<>();
  
  Section lastSection = null;
  
  while( reader.readRow(parts) != null )
  {
   if( isEmptyLine(parts) )
    continue;

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
   
   if( subm == null && ! classRef.getValue().equals(SubmissionTag))
    throw new ParserException(reader.getLineNumber(), 1, "First block should be '"+SubmissionTag+"'");
   
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
      
      block.getRecord(cells4);
     }
    }
   }
   
   Annotated blockObj = null;
   
   
   if( cells1.size() == 0 )
    throw new ParserException(reader.getLineNumber(), 1, "No data in block");

   CellValue cell0 = cells1.get(0);
   
   if( cell0.getValue().equals(SubmissionTag) )
   {
    if( subm != null )
     throw new ParserException(cell0.getRow(), cell0.getCol(), "Repeating block: '"+SubmissionTag+"'");
    
    subm = new Submission();
    blockObj = subm;
    
    if( cells2.size() > 0 )
    {
     String acc = cells2.get(0).getValue();
     
     if( acc.length() > 0 )
      subm.setAcc( cells2.get(0).getValue() );
     else
      throw new ParserException(cells2.get(0).getRow(), cells2.get(0).getCol(), "Missing submission ID");
 
     if( cells3.size() > 0 && cells3.get(0).getValue().length() > 0 )
      throw new ParserException(cells3.get(0).getRow(), cells3.get(0).getCol(), "Unexpected value. Expecting blank");
    }
    else
     throw new ParserException(cells2.get(0).getRow(), cells2.get(0).getCol(), "Missing submission ID");

   }
   else if( subm == null )
    throw new ParserException(reader.getLineNumber(), 1, "First block should be '"+SubmissionTag+"'");
   else if( cell0.getValue().equals(FileTag) )
   {
    FileRef fr = new FileRef();
    
    if( cells2.size() > 0 )
    {
     String nm = cells2.get(0).getValue();
     
     if( nm.length() > 0 )
      fr.setName( cells2.get(0).getValue() );
 
     if( cells3.size() > 0 )
      s.setParentAcc( cells3.get(0).getValue() );
    }
    
    blockObj = fr;
   }
   else if( cell0.getValue().equals(LinkTag) )
   {
    Link l = new Link();
    
    
    
    blockObj = l;
   }
   else
   {
    Section s = new Section();
    lastSection = s;
    
    s.setType(cell0.getValue());
    
    if( cells2.size() > 0 )
    {
     String acc = cells2.get(0).getValue();
     
     if( acc.length() > 0 )
      s.setAcc( cells2.get(0).getValue() );
 
     if( cells3.size() > 0 )
      s.setParentAcc( cells3.get(0).getValue() );
    }
    
    
    blockObj = s;
   }
   

   
   
   while( block.getRecord(cells) != null )
   {
    Iterator<CellValue> cellIter = cells.iterator();
    
    CellValue cell = cellIter.next();
    
    cell.trim();
    
    if( cell.getValue().length() != 0 )
    {
     if( cell.matchString(profileDef.getAnonymousObjectId()) )
     { 
      String id = "??"+IdGenerator.getInstance().getStringId("tempObjectId");
      cObj = data.createObject( id, header, block.getOrder(cell) );
      cObj.setIdDefined(false);
      cObj.setIdScope(IdScope.MODULE);
     }
     else
     {
      boolean defined = ! cell.matchSubstring(profileDef.getAnonymousObjectId(), 0);
      
      IdScope scope = defined? profileDef.getDefaultIdScope() : IdScope.MODULE;

      String pfx = profileDef.getGlobalIdPrefix();
      
      String id = cell.getValue();
      
      if( cell.matchSubstring(pfx,0) )
      {
       id = cell.getValue().substring(pfx.length());
       scope = IdScope.GLOBAL;
      }
      else
      {
       pfx = profileDef.getClusterIdPrefix();
       
       if( cell.matchSubstring(pfx,0) )
       {
        id = cell.getValue().substring(pfx.length());
        scope = IdScope.CLUSTER;
       }
       else
       {
        pfx = profileDef.getModuleIdPrefix();
        
        if( cell.matchSubstring(pfx,0) )
        {
         id = cell.getValue().substring(pfx.length());
         scope = IdScope.MODULE;
        }
        else
        {
         pfx = profileDef.getDefaultScopeIdPrefix();
         
         if( cell.matchSubstring(pfx,0) )
         {
          id = cell.getValue().substring(pfx.length());
          scope = profileDef.getDefaultIdScope();
         }
        }
       }
      }
      
   

      cObj = data.getOrCreateObject(id,header,block.getOrder(cell) );
      
      cObj.setIdScope(scope);
      cObj.setIdDefined( defined );
      cObj.setPrototype( cell.matchString( profileDef.getPrototypeObjectId() ) );
     }
    }
    else if( cObj == null )
     throw new ParserException(cell.getRow(), cell.getCol(), "Object identifier is expected");
   
    for( ClassReference prop : header.getColumnHeaders() )
    {
     
     if(!cellIter.hasNext())
      break;
     
     cell = cellIter.next();

     if( prop != null )
     {
//      if( cell.getValue().length() > 0 )
      cObj.addValue( new AgeTabValue(cell.getRow(), cell.getCol() , prop, cell)  );
     }
     else if( cell.getValue().trim().length() > 0 )
     {
      throw new ParserException(cell.getRow(), cell.getCol(),"Not empty value in the empty-headed column");
     }
     
    }
   }
   
  }

  return data;
 }

 private ClassReference createClassReference( CellValue cell, SyntaxProfileDefinition profDef ) throws ParserException
 {
  int embedSepLen = profDef.getDefaultEmbeddedObjectAttributeSeparator().length(); 
  
   String rawVal = cell.getRawValue();
   
   ClassReference embeddedProperty=null;
   
   int offs=0;
   while( true )
   {
    int pos = rawVal.indexOf( profDef.getDefaultEmbeddedObjectAttributeSeparator(), offs );
    
    if( pos == -1 )
     break;
    
    offs = pos+embedSepLen;

    if( ! cell.hasRed(pos, offs) )
    {
     embeddedProperty = createClassReference( new CellValue(cell.getRawValue().substring(offs), profDef.getEscapeSequence(),cell.getRow(),cell.getCol()), profDef );
     cell =  new CellValue(cell.getRawValue().substring(0,pos), profDef.getEscapeSequence(), cell.getRow(), cell.getCol() );
     break;
    }
    
   }
  
   if( cell.getValue().trim().length() == 0 )
    return null;
   
   ClassReference partName = null;
   
   try
   {
    partName = string2ClassReference(cell);
    partName.setRawReference(rawVal);

    partName.setRow(cell.getRow());
    partName.setCol(cell.getCol());

    if( partName.getQualifiers() != null )
    {
     for( ClassReference qref : partName.getQualifiers() )
     {
      qref.setRow(cell.getRow());
      qref.setCol(cell.getCol());
     }
    }
    
    if( embeddedProperty != null )
     partName.setEmbeddedClassRef(embeddedProperty);
   }
   catch(ParserException e)
   {
    e.setLineNumber(cell.getRow());
    e.setColumn(cell.getCol());

    throw e;
   }

   return partName;
 }
 
 private void analyzeHeader(BlockHeader hdr, List<CellValue> parts) throws ParserException
 {
  Iterator<CellValue> itr = parts.iterator();
  
  CellValue cell = itr.next();
  
  
  ClassReference partName;
  try
  {
   partName = string2ClassReference( cell );
   partName.setHorizontal(hdr.isHorizontal());
   partName.setRow(cell.getRow());
   partName.setCol(cell.getCol());
   partName.setRawReference(cell.getRawValue());
  }
  catch(ParserException e)
  {
   e.setLineNumber(cell.getRow());
   e.setColumn(cell.getCol());
   throw e;
  }
  
  
  hdr.setClassColumnHeader(partName);
  
  SyntaxProfileDefinition profDef = partName.isCustom()?getSyntaxProfile().getCommonSyntaxProfile():getSyntaxProfile().getClassSpecificSyntaxProfile(partName.getName());
  
  while( itr.hasNext() )
  {
   cell = itr.next();

   hdr.addColumnHeader( createClassReference(cell, profDef) );
  }
 }

 
 private static boolean isEmptyLine( List<String> parts )
 {
  for(String pt : parts )
   if( pt.length() != 0 )
    return false;
  
  return true;
 }
 
}

