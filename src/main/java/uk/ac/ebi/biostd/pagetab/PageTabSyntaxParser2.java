package uk.ac.ebi.biostd.pagetab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.Tag;
import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.context.BlockContext;
import uk.ac.ebi.biostd.pagetab.context.BlockContext.BlockType;
import uk.ac.ebi.biostd.pagetab.context.FileContext;
import uk.ac.ebi.biostd.pagetab.context.LinkContext;
import uk.ac.ebi.biostd.pagetab.context.SectionContext;
import uk.ac.ebi.biostd.pagetab.context.SubmissionContext;
import uk.ac.ebi.biostd.pagetab.context.VoidContext;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;
import uk.ac.ebi.biostd.util.StringUtils;

public class PageTabSyntaxParser2
{

 public static final String HorizontalBlockPrefix = "-";
 public static final String VerticalBlockPrefix   = "|";

 public static final String TagSeparatorRX        = "[,;]";
 public static final String ClassifierSeparatorRX = ":";
 public static final String ValueTagSeparatorRX   = "=";

 public static final String GeneratedAccNoPattern = "(?<tmpid>[^{]+)?(?:\\{(?<pfx>[^,}]+)(?:,(?<sfx>[^}]+))?\\})?";
 public static final String NameQualifierPattern  = "\\<(?<nameq>[^>]+)\\>";
 public static final String ValueQualifierPattern = "\\[(?<valueq>[^>]+)\\]";
 public static final String TableBlockPattern     = "(?<name>[^\\s[]+)\\[(?<parent>[^\\]])?\\]";

 public static final String SubmissionKeyword     = "Submission";
 public static final String FileKeyword           = "File";
 public static final String LinkKeyword           = "Link";

 private final TagResolver  tagRslv;
 private final ParserConfig config;

 private final Matcher      nameQualMtch;
 private final Matcher      valueQualMtch;
 private final Matcher      tableBlockMtch;

 public PageTabSyntaxParser2(TagResolver tr, ParserConfig pConf)
 {
  tagRslv = tr;

  nameQualMtch = Pattern.compile(NameQualifierPattern).matcher("");
  valueQualMtch = Pattern.compile(ValueQualifierPattern).matcher("");
  tableBlockMtch = Pattern.compile(TableBlockPattern).matcher("");

  config = pConf;
 }

 public Submission parse( String txt, LogNode ln ) //throws ParserException
 {
  Submission subm = null;
  
  Map<String, SectionRef> secMap = new HashMap<>();
  
  SpreadsheetReader reader = new SpreadsheetReader(txt);

  BlockContext context = new VoidContext();
  
  List<String> parts = new ArrayList<String>(100);

  int lineNo = 0;
  
  Section lastSection = null;
  
  while( reader.readRow(parts) != null )
  {
   lineNo++;
   
   if( isEmptyLine(parts) )
   {
    if( context.getBlockType() != BlockType.NONE )
    {
     context.finish();
     context = new VoidContext();
    }
    
    continue;
   }
  
   if( context.getBlockType() == BlockType.NONE )
   {
    String c0 = parts.get(0).trim();

    if( c0.length() == 0 )
     ln.log(Level.ERROR, "(R"+lineNo+",C1) Empty cell is not expected here. Should be a block type");
    
    if( c0.equals(SubmissionKeyword) )
    {
     LogNode sln = ln.branch("(R"+lineNo+",C1) Processing '"+SubmissionKeyword+"' block");
     
     if( subm != null )
      sln.log(Level.ERROR, "(R"+lineNo+",C1) Multiple blocks: '"+SubmissionKeyword+"' are not allowed");
     
     subm = new Submission();
     
     context = new SubmissionContext( subm, this, sln );
     
     context.parseFirstLine( parts, lineNo );
    }
    else if( c0.equals(FileKeyword) )
    {
     LogNode sln = ln.branch("(R"+lineNo+",C1) Processing '"+FileKeyword+"' block");
     
     if( lastSection == null )
      sln.log(Level.ERROR, "(R"+lineNo+",C1) '"+FileKeyword+ "' block should follow any section block");

     FileRef fr = new FileRef();

     context = new FileContext( fr, this, sln );
     
     context.parseFirstLine( parts, lineNo );
    }
    else if( c0.equals(LinkKeyword) )
    {
     LogNode sln = ln.branch("(R"+lineNo+",C1) Processing '"+LinkKeyword+"' block");
     
     if( lastSection == null )
      sln.log(Level.ERROR, "(R"+lineNo+",C1) '"+LinkKeyword+ "' block should follow any section block");

     Link lnk = new Link();

     context = new LinkContext( lnk, this, sln );
     
     context.parseFirstLine( parts, lineNo );
    }
    else
    {
     tableBlockMtch.reset(c0);
     
     if( tableBlockMtch.matches() )
     {
      String sName = tableBlockMtch.group("name").trim();
      String pAcc = tableBlockMtch.group("parent").trim();

      LogNode sln = ln.branch("(R"+lineNo+",C1) Processing '"+sName+"' table block");
      
      Section pSec = lastSection;
      
      if( pAcc.length() > 0 )
      {
       SectionRef pSecRef = secMap.get( pAcc );
       
       if( pSec == null )
        sln.log(Level.ERROR, "(R" + lineNo + ",C3) Parent section '" + pAcc + "' not found");

      }
      
      context = new SectionTableContext(this, sln);
      
     }
     else
     {
      LogNode sln = ln.branch("(R"+lineNo+",C1) Processing '"+c0+"' section block");

      Section s = new Section();

      if( lastSection == null )
      {
       if( subm == null )
        sln.log(Level.ERROR, "(R"+lineNo+",C1) Section must follow '"+SubmissionKeyword+"' block or other section block");
       else
        subm.setRootSection(s);
      }
      
      context = new SectionContext( s, this, sln );
      
      s.setType(c0);
      
      if( s.getAcc() != null )
      {
       if( secMap.containsKey( s.getAcc() ) )
        sln.log(Level.ERROR, "(R"+lineNo+",C2) Section accession number '"+s.getAcc()+"' is arleady used for another object");
       else
        secMap.put(s.getAcc(), new SectionRef(s) );
      }
      
      if( s.getParentAcc() != null )
      {
       SectionRef psec = secMap.get( s.getParentAcc() );
       
       if( psec != null )
        psec.getSection().addSection(s);
       else
        sln.log(Level.ERROR, "(R" + lineNo + ",C3) Parent section '" + s.getParentAcc() + "' not found");
      }
      
     lastSection = s;
     
    }
     }
    
   }
  
  }

  
  
  return subm;
 }
 
 private static boolean isEmptyLine( List<String> parts )
 {
  for(String pt : parts )
   if( pt.trim().length() != 0 )
    return false;
  
  return true;
 }
 
 
 public <T extends TagRef> List<T> processTags(List<String> cells, int r, int c, TagReferenceFactory<T> tagRefFact, LogNode ln)
 {
  String cell = cells.size() >= c ?cells.get(c).trim():null;
  
  List<T> tags = null;
  
  if( cell != null && cell.length() > 0 )
  {
   if( tagRslv == null )
    ln.log(Level.WARN, "(R"+r+",C"+c+") Tag resolver is not configured. Tags will be ignored");
   else
   {
    LogNode acNode = ln.branch("Resolving tags");
    tags = resolveTags(cell, r, c, config.missedTagLL(), tagRefFact, acNode );
    
    acNode.success();
   }
  }
  
  return tags;
 }
 
 public Collection<AccessTag> processAccessTags(List<String> cells, int r, int c, LogNode ln)
 {
  String cell = cells.size() >= c?cells.get(c-1).trim():null;
  
  List<AccessTag> tags = null;
  
  if( cell != null && cell.length() > 0 )
  {
   if( tagRslv == null )
    ln.log(Level.WARN, "(R"+r+",C"+c+") Tag resolver is not configured. Access tags will be ignored");
   else
   {
    LogNode acNode = ln.branch("Resolving access tags");
    tags = resolveAccessTags(cell, config.missedAccessTagLL(), r, c, acNode );

    acNode.success();
   }
  }

  return tags;
 }

 public <T extends TagRef> List<T> resolveTags(String cell, int r, int c, Level missedTagLL, TagReferenceFactory<T> tagRefFact, LogNode acNode)
 {
  String[] tags = cell.split("(?<!\\\\)"+TagSeparatorRX);
  
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
    acNode.log(Level.WARN, "(R"+r+",C"+c+") Invalid tag reference: '"+nm+"'");
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
    
    acNode.log(Level.INFO, "(R"+r+",C"+c+") Tag resolved: '"+nm+"'");
   }
   else
    acNode.log(missedTagLL, "(R"+r+",C"+c+") Tag not resolved: '"+nm+"'");
   
  }
  
  return res;
 }

 public List<AccessTag> resolveAccessTags(String value,  LogNode.Level ll, int r, int c, LogNode acNode)
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
}
