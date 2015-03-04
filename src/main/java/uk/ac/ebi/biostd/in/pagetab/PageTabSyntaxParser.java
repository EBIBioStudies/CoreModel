package uk.ac.ebi.biostd.in.pagetab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.Tag;
import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.db.TagResolver;
import uk.ac.ebi.biostd.in.CellPointer;
import uk.ac.ebi.biostd.in.PMDoc;
import uk.ac.ebi.biostd.in.Parser;
import uk.ac.ebi.biostd.in.ParserConfig;
import uk.ac.ebi.biostd.in.ParserException;
import uk.ac.ebi.biostd.in.pagetab.context.BlockContext;
import uk.ac.ebi.biostd.in.pagetab.context.BlockContext.BlockType;
import uk.ac.ebi.biostd.in.pagetab.context.FileContext;
import uk.ac.ebi.biostd.in.pagetab.context.FileTableContext;
import uk.ac.ebi.biostd.in.pagetab.context.LinkContext;
import uk.ac.ebi.biostd.in.pagetab.context.LinkTableContext;
import uk.ac.ebi.biostd.in.pagetab.context.SectionContext;
import uk.ac.ebi.biostd.in.pagetab.context.SectionTableContext;
import uk.ac.ebi.biostd.in.pagetab.context.SubmissionContext;
import uk.ac.ebi.biostd.in.pagetab.context.VoidContext;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;
import uk.ac.ebi.biostd.treelog.SimpleLogNode;
import uk.ac.ebi.biostd.util.StringUtils;

public class PageTabSyntaxParser extends Parser
{

 public static final String TagSeparatorRX        = "[,;]";
 public static final String ClassifierSeparatorRX = ":";
 public static final String ValueTagSeparatorRX   = "=";
 public static final String CommentPrefix   = "#";
 public static final String DocParamPrefix   = "#@";

 public static final String NameQualifierRx  = "\\s*\\(\\s*(?<name>[^\\)]+)\\s*\\)\\s*";
 public static final String ValueQualifierRx = "\\s*\\[\\s*(?<name>[^\\]]+)\\s*\\]\\s*";
 public static final String ReferenceRx = "\\s*\\<\\s*(?<name>[^\\>]+)\\s*\\>\\s*";
 public static final String TableBlockRx     = "\\s*(?<name>[^\\s\\[]+)\\[\\s*(?<parent>[^\\]\\s]+)?\\s*\\]\\s*";

 public static final String SubmissionKeyword     = "Submission";
 public static final String FileKeyword           = "File";
 public static final String LinkKeyword           = "Link";
 public static final String LinkTableKeyword      = "Links";
 public static final String FileTableKeyword      = "Files";

 private final TagResolver  tagRslv;
 private final ParserConfig config;

 
 public static final Pattern NameQualifierPattern = Pattern.compile(NameQualifierRx) ;
 public static final Pattern ValueQualifierPattern = Pattern.compile(ValueQualifierRx) ;
 public static final Pattern TableBlockPattern = Pattern.compile(TableBlockRx) ;
 public static final Pattern ReferencePattern = Pattern.compile(ReferenceRx) ;
 public static final Pattern GeneratedAccNo = Pattern.compile(GeneratedAccNoRx);


 public PageTabSyntaxParser(TagResolver tr, ParserConfig pConf)
 {
  tagRslv = tr;

  config = pConf;
 }

 @Override
 public PMDoc parse( String txt, LogNode topLn ) throws ParserException
 {
  Matcher tableBlockMtch = Pattern.compile(TableBlockRx).matcher("");
  Matcher genAccNoMtch = GeneratedAccNo.matcher("");
  
  ParserState pstate = new ParserState();
  
  pstate.setParser(this);
  pstate.setNameQualifierMatcher(NameQualifierPattern.matcher(""));
  pstate.setValueQualifierMatcher(ValueQualifierPattern.matcher(""));
  pstate.setReferenceMatcher(ReferencePattern.matcher(""));
  pstate.setGeneratedAccNoMatcher(genAccNoMtch);
  
  PMDoc res = new PMDoc();
  
  SubmissionInfo submInf = null;

  SpreadsheetReader reader = null;
  
  if( txt.startsWith("<?xml"))
  {
   try
   {
    reader = new XMLSpreadsheetReader(txt);
   }
   catch(Exception e)
   {
    topLn.log(Level.ERROR, "Invalid XML");
    return null;
   }
  }
  else
   reader = new CSVTSVSpreadsheetReader(txt);

  BlockContext context = new VoidContext();

  List<String> parts = new ArrayList<String>(100);

//  Map<String,SectionContext> parentsSectionMap = new HashMap<String, SectionContext>();
  
  int lineNo = 0;

//  Section lastSection = null;
  
//  SectionContext lastSecContext = null;
  
  SectionOccurrence lastSectionOccurance = null;
  
  SubmissionContext lastSubmissionContext = null;
  
  while(reader.readRow(parts) != null)
  {
   lineNo++;

   if( parts.size() > 0 && parts.get(0).startsWith(DocParamPrefix))
   {
    String pVal = parts.size()>1?parts.get(1):null;
    
    res.addHeader(parts.get(0).substring(DocParamPrefix.length()),pVal);
    
    continue;
   }
   
   for( int i=0; i < parts.size(); i++ )
   {
    if( parts.get(i).startsWith(CommentPrefix) )
     parts.set(i, "");
   }
   
   if(isEmptyLine(parts))
   {
    if(context.getBlockType() != BlockType.NONE)
    {
     context.finish();
     
//     BlockContext pc=null;
     
//     if( context.getBlockType() == BlockType.SUBMISSION || context.getBlockType() == BlockType.SECTION )
//      pc = context;
//     else
//      pc = context.getParentContext();
      
     context = new VoidContext( );
    }

    continue;
   }

   if(context.getBlockType() == BlockType.NONE) // we are not processing any block and found first non empty line
   {
    String c0 = parts.get(0).trim();

    if(c0.length() == 0)
    {
     LogNode ln = submInf!=null? submInf.getLogNode() : topLn;
     ln.log(Level.ERROR, "(R" + lineNo + ",C1) Empty cell is not expected here. Should be a block type");
    }

    if(c0.equals(SubmissionKeyword))
    {
     LogNode ln = topLn.branch("(R" + lineNo + ",C1) Processing '" + SubmissionKeyword + "' block");

     if(submInf != null )
     {
      finalizeSubmission(submInf);

      res.addSubmission(submInf);
      
      if ( ! config.isMultipleSubmissions() )
       ln.log(Level.ERROR, "(R" + lineNo + ",C1) Multiple blocks: '" + SubmissionKeyword + "' are not allowed");
     }

     Submission subm = new Submission();
     submInf = new SubmissionInfo( subm );
     submInf.setLogNode(ln);

     lastSubmissionContext = new SubmissionContext(submInf, pstate, ln);
     context=lastSubmissionContext;

     context.parseFirstLine(parts, lineNo);
     
     if( subm.getAccNo() != null )
     {
      ln.log(Level.INFO, "Submission AccNo: "+subm.getAccNo());
     
      genAccNoMtch.reset(subm.getAccNo());
      
      if( genAccNoMtch.matches() )
      {
       subm.setAccNo(genAccNoMtch.group("tmpid"));
       submInf.setAccNoPrefix(genAccNoMtch.group("pfx"));
       submInf.setAccNoSuffix(genAccNoMtch.group("sfx"));
      }
     }
      
     lastSectionOccurance = null;
     
     continue;
    }
    
    if( submInf == null )
    {
     topLn.log(Level.ERROR, "(R" + lineNo + ",C1) Block is defined out of submission context");
     
     Submission subm = new Submission();
     submInf = new SubmissionInfo( subm );
     submInf.setLogNode(topLn);
    }
    
    if(c0.equals(FileKeyword))
    {
     LogNode pln = lastSectionOccurance!=null?lastSectionOccurance.getSecLogNode(): (submInf!=null?submInf.getLogNode():topLn);
     
     LogNode sln = pln.branch("(R" + lineNo + ",C1) Processing '" + FileKeyword + "' block");

     if(lastSectionOccurance == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) '" + FileKeyword + "' block should follow any section block");


     FileRef fr = new FileRef();

     context = new FileContext(fr, submInf, pstate, sln);

     context.parseFirstLine(parts, lineNo);
     
     if(lastSectionOccurance != null)
      lastSectionOccurance.getSection().addFileRef(fr);
    }
    else if(c0.equals(LinkKeyword))
    {
     LogNode pln = lastSectionOccurance!=null?lastSectionOccurance.getSecLogNode(): (submInf!=null?submInf.getLogNode():topLn);
     
     LogNode sln = pln.branch("(R" + lineNo + ",C1) Processing '" + LinkKeyword + "' block");

     if(lastSectionOccurance == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) '" + LinkKeyword + "' block should follow any section block");

     Link lnk = new Link();

     context = new LinkContext(lnk, submInf, pstate, sln);

     context.parseFirstLine(parts, lineNo);
     
     if(lastSectionOccurance != null)
      lastSectionOccurance.getSection().addLink(lnk);
    }
    else if(c0.equals(LinkTableKeyword))
    {
     LogNode pln = lastSectionOccurance!=null?lastSectionOccurance.getSecLogNode(): (submInf!=null?submInf.getLogNode():topLn);
     
     LogNode sln = pln.branch("(R" + lineNo + ",C1) Processing links table block");

     if(lastSectionOccurance == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) Link table must follow section block");


     context = new LinkTableContext(lastSectionOccurance!=null?lastSectionOccurance.getSection():null, submInf, pstate, sln);
     context.parseFirstLine(parts, lineNo);
    }
    else if(c0.equals(FileTableKeyword))
    {
     LogNode pln = lastSectionOccurance!=null?lastSectionOccurance.getSecLogNode(): (submInf!=null?submInf.getLogNode():topLn);
     
     LogNode sln = pln.branch("(R" + lineNo + ",C1) Processing files table block");

     if(lastSectionOccurance == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) File table must follow section block");

     context = new FileTableContext(lastSectionOccurance!=null?lastSectionOccurance.getSection():null, submInf, pstate, sln);
     context.parseFirstLine(parts, lineNo);
    }
    else
    {
     tableBlockMtch.reset(c0);

     if(tableBlockMtch.matches())
     {
      String sName = tableBlockMtch.group("name").trim();
      String pAcc = tableBlockMtch.group("parent");
      
      if( pAcc != null )
       pAcc = pAcc.trim();

      LogNode pln = lastSectionOccurance!=null?lastSectionOccurance.getSecLogNode(): (submInf!=null?submInf.getLogNode():topLn);
      
      LogNode sln = pln.branch("(R" + lineNo + ",C1) Processing '" + sName + "' table block");

      if(lastSectionOccurance == null)
       sln.log(Level.ERROR, "(R" + lineNo + ",C1) Sections table must follow any section block");


      SectionOccurrence parentSecOcc = lastSectionOccurance;
      
      if(pAcc != null && pAcc.length() > 0)
      {
       parentSecOcc = submInf.getNonTableSection(pAcc);
       
       if(parentSecOcc == null)
        sln.log(Level.ERROR, "(R" + lineNo + ",C1) Parent section '" + pAcc + "' not found");
       else if( pln != parentSecOcc.getSecLogNode() )
        sln.move(pln, parentSecOcc.getSecLogNode());

      }

      context = new SectionTableContext(sName, parentSecOcc, submInf, pstate, sln);
      context.parseFirstLine(parts, lineNo);

     }
     else
     {
      
      LogNode pln = lastSectionOccurance!=null?lastSectionOccurance.getSecLogNode(): (submInf!=null?submInf.getLogNode():topLn);

      LogNode sln = pln.branch("(R" + lineNo + ",C1) Processing '" + c0 + "' section block");

      
      Section s = new Section();
      
      s.setGlobal(false);

      SectionOccurrence secOc = new SectionOccurrence();
      
      secOc.setElementPointer( new CellPointer(lineNo, 1));
      secOc.setSection(s);
      secOc.setSecLogNode(sln);
      
      if(lastSectionOccurance == null)
      {
       if(submInf == null )
        sln.log(Level.ERROR, "(R" + lineNo + ",C1) Section must follow '" + SubmissionKeyword + "' block or other section block");
       else 
        submInf.getSubmission().setRootSection(s);
      }

      context = new SectionContext(s, submInf, pstate, sln);

      context.parseFirstLine(parts, lineNo);
      
      s.setType(c0);

      if(s.getAccNo() != null)
      {
       genAccNoMtch.reset( s.getAccNo() );
       
       
       if( genAccNoMtch.matches() )
       {
        s.setGlobal(true);
        
        String pfx = genAccNoMtch.group("pfx");
        String sfx = genAccNoMtch.group("sfx");
        
        secOc.setPrefix(pfx);
        secOc.setSuffix(sfx);
        
        s.setAccNo(genAccNoMtch.group("tmpid"));
        
        
        if( pfx != null && pfx.length() > 0 )
        { 
         if( Character.isDigit( pfx.charAt(pfx.length()-1) ) )
          sln.log(Level.ERROR, "(R" + lineNo + ",C2) Accession number prefix can't end with a digit '" + pfx + "'");
        }
        
        if( sfx != null && sfx.length() > 0 ) 
        { 
         if( Character.isDigit( sfx.charAt(0) ) )
          sln.log(Level.ERROR, "(R" + lineNo + ",C2) Accession number suffix can't start with a digit '" + sfx + "'");
        }
        
        if( submInf != null )
         submInf.addGlobalSection(secOc);
       }

      }
      
      String pAcc = s.getParentAccNo();
      SectionOccurrence pSecCtx = lastSectionOccurance;
      
      if(pAcc != null && pAcc.length() > 0)
      {
       pSecCtx = submInf.getNonTableSection(pAcc);

       if(pSecCtx == null)
        sln.log(Level.ERROR, "(R" + lineNo + ",C3) Parent section '" + pAcc + "' not found");
       else if( pln != pSecCtx.getSecLogNode() )
        sln.move(pln, pSecCtx.getSecLogNode());
      }
      
      
      if( pSecCtx != null )
       pSecCtx.getSection().addSection(s);
      
      
      if(s.getAccNo() != null)
      {
       if(submInf.getSectionOccurance(s.getAccNo()) != null)
        sln.log(Level.ERROR, "Accession number '" + s.getAccNo() + "' is used by other section at " + secOc.getElementPointer());

       submInf.addNonTableSection(secOc);
       submInf.addSectionOccurance(secOc);
      }

      
      lastSectionOccurance=secOc;

     }
    }

   }
   else  // Some non-void context is active
   {
    context.parseLine(parts,lineNo);
   }

  }

  if( submInf != null )
  {
   finalizeSubmission(submInf);
  
   res.addSubmission(submInf);
  }
  
  SimpleLogNode.setLevels(topLn);
  
  
  return res;
 }
 
 private void finalizeSubmission( SubmissionInfo si )
 {
  if( si.getReferenceOccurrences() != null )
  {
   for( ReferenceOccurrence r : si.getReferenceOccurrences() )
   {
    SectionOccurrence soc = si.getSectionOccurance(r.getRef().getValue());
    if( soc == null )
     r.getLogNode().log(Level.ERROR, r.getElementPointer()+" Invalid reference. Target doesn't exist: '"+r.getRef()+"'");
    else
     r.setSection( soc.getSection() );
   }
  }
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
  String cell = cells.size() >= c ?cells.get(c-1).trim():null;
  
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
   else
    nm = t;
   
   
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
