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
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.context.BlockContext;
import uk.ac.ebi.biostd.pagetab.context.BlockContext.BlockType;
import uk.ac.ebi.biostd.pagetab.context.FileContext;
import uk.ac.ebi.biostd.pagetab.context.FileTableContext;
import uk.ac.ebi.biostd.pagetab.context.LinkContext;
import uk.ac.ebi.biostd.pagetab.context.LinkTableContext;
import uk.ac.ebi.biostd.pagetab.context.SectionContext;
import uk.ac.ebi.biostd.pagetab.context.SectionTableContext;
import uk.ac.ebi.biostd.pagetab.context.SubmissionContext;
import uk.ac.ebi.biostd.pagetab.context.VoidContext;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;
import uk.ac.ebi.biostd.treelog.SimpleLogNode;
import uk.ac.ebi.biostd.util.StringUtils;

public class PageTabSyntaxParser2
{

 public static final String TagSeparatorRX        = "[,;]";
 public static final String ClassifierSeparatorRX = ":";
 public static final String ValueTagSeparatorRX   = "=";
 public static final String CommentPrefix   = "#";

 public static final String GeneratedAccNoRx = "\\s*!(?<tmpid>[^{]+)?(?:\\{(?<pfx>[^,}]+)?(?:,(?<sfx>[^}]+))?\\})?\\s*";
 public static final String NameQualifierRx  = "\\s*\\(\\s*(?<name>[^\\s>]+)\\s*\\)\\s*";
 public static final String ValueQualifierRx = "\\s*\\[\\s*(?<name>[^\\s>]+)\\s*\\]\\s*";
 public static final String ReferenceRx = "\\s*\\<\\s*(?<name>[^\\s>]+)\\s*\\>\\s*";
 public static final String TableBlockRx     = "\\s*(?<name>[^\\s\\[]+)\\[\\s*(?<parent>[^\\]\\s]+)?\\s*\\]\\s*";

 public static final String SubmissionKeyword     = "Submission";
 public static final String FileKeyword           = "File";
 public static final String LinkKeyword           = "Link";
 public static final String LinkTableKeyword      = "Links";
 public static final String FileTableKeyword      = "Files";

 private final TagResolver  tagRslv;
 private final ParserConfig config;

 private final Matcher      tableBlockMtch;
 private final Matcher      genAccNoMtch;
 
 public static final Pattern NameQualifierPattern = Pattern.compile(NameQualifierRx) ;
 public static final Pattern ValueQualifierPattern = Pattern.compile(ValueQualifierRx) ;
 public static final Pattern TableBlockPattern = Pattern.compile(TableBlockRx) ;
 public static final Pattern ReferencePattern = Pattern.compile(ReferenceRx) ;


 public PageTabSyntaxParser2(TagResolver tr, ParserConfig pConf)
 {
  tagRslv = tr;


  tableBlockMtch = Pattern.compile(TableBlockRx).matcher("");
  genAccNoMtch = Pattern.compile(GeneratedAccNoRx).matcher("");

  config = pConf;
 }

 public List<SubmissionInfo> parse( String txt, LogNode topLn ) //throws ParserException
 {
  List<SubmissionInfo> res = new ArrayList<>(10);
  
  SubmissionInfo submInf = new SubmissionInfo(null);

  SpreadsheetReader reader = new SpreadsheetReader(txt);

  BlockContext context = new VoidContext(topLn, null);

  List<String> parts = new ArrayList<String>(100);

  Map<String,SectionContext> secMap = new HashMap<String, SectionContext>();
  
  int lineNo = 0;

//  Section lastSection = null;
  
  SectionContext lastSecContext = null;
  SubmissionContext lastSubmissionContext = null;
  
  while(reader.readRow(parts) != null)
  {
   lineNo++;

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
     
     BlockContext pc=null;
     
     if( context.getBlockType() == BlockType.SUBMISSION || context.getBlockType() == BlockType.SECTION )
      pc = context;
     else
      pc = context.getParentContext();
      
     context = new VoidContext( pc.getContextLogNode(), pc );
    }

    continue;
   }

   if(context.getBlockType() == BlockType.NONE)
   {
    String c0 = parts.get(0).trim();

    if(c0.length() == 0)
     context.getContextLogNode().log(Level.ERROR, "(R" + lineNo + ",C1) Empty cell is not expected here. Should be a block type");

    if(c0.equals(SubmissionKeyword))
    {
     LogNode ln = context.getContextLogNode().branch("(R" + lineNo + ",C1) Processing '" + SubmissionKeyword + "' block");

     if(submInf.getSubmission() != null && ! config.isMultipleSubmissions() )
      ln.log(Level.ERROR, "(R" + lineNo + ",C1) Multiple blocks: '" + SubmissionKeyword + "' are not allowed");

     Submission subm = new Submission();
     submInf = new SubmissionInfo( subm );

     lastSubmissionContext = new SubmissionContext(subm, this, ln, context);
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
      
     lastSecContext = null;
     
     res.add(submInf);
    }
    else if(c0.equals(FileKeyword))
    {
     LogNode sln = context.getContextLogNode().branch("(R" + lineNo + ",C1) Processing '" + FileKeyword + "' block");

     if(lastSecContext == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) '" + FileKeyword + "' block should follow any section block");

     FileRef fr = new FileRef();

     context = new FileContext(fr, this, sln, context.getParentContext());

     context.parseFirstLine(parts, lineNo);
     
     if(lastSecContext != null)
      lastSecContext.getSection().addFileRef(fr);
    }
    else if(c0.equals(LinkKeyword))
    {
     LogNode sln = context.getContextLogNode().branch("(R" + lineNo + ",C1) Processing '" + LinkKeyword + "' block");

     if(lastSecContext == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) '" + LinkKeyword + "' block should follow any section block");

     Link lnk = new Link();

     context = new LinkContext(lnk, this, sln, context.getParentContext());

     context.parseFirstLine(parts, lineNo);
     
     if(lastSecContext != null)
      lastSecContext.getSection().addLink(lnk);
    }
    else if(c0.equals(LinkTableKeyword))
    {
     LogNode sln = context.getContextLogNode().branch("(R" + lineNo + ",C1) Processing links table block");

     if(lastSecContext == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) Link table must follow section block");

     context = new LinkTableContext(lastSecContext!=null?lastSecContext.getSection():null, this, sln, context.getParentContext());
     context.parseFirstLine(parts, lineNo);
    }
    else if(c0.equals(FileTableKeyword))
    {
     LogNode sln = context.getContextLogNode().branch("(R" + lineNo + ",C1) Processing files table block");

     if(lastSecContext == null)
      sln.log(Level.ERROR, "(R" + lineNo + ",C1) File table must follow section block");

     context = new FileTableContext(lastSecContext!=null?lastSecContext.getSection():null, this, sln, context.getParentContext());
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

      LogNode sln = context.getContextLogNode().branch("(R" + lineNo + ",C1) Processing '" + sName + "' table block");

      if(lastSecContext == null)
       sln.log(Level.ERROR, "(R" + lineNo + ",C1) Sections table must follow section block");

      SectionContext pSecCtx = lastSecContext;

      if(pAcc != null && pAcc.length() > 0)
      {
       pSecCtx = secMap.get(pAcc);

       if(pSecCtx == null)
        sln.log(Level.ERROR, "(R" + lineNo + ",C3) Parent section '" + pAcc + "' not found");
       else if( context.getContextLogNode() != pSecCtx.getContextLogNode() )
       {
        sln.move(context.getContextLogNode(), pSecCtx.getContextLogNode());
       }

      }

      context = new SectionTableContext(sName, pSecCtx==null?null:pSecCtx.getSection(), submInf, this, sln, pSecCtx);
      context.parseFirstLine(parts, lineNo);

     }
     else
     {
      BlockContext curCtx = context;
      
      LogNode sln = context.getContextLogNode().branch("(R" + lineNo + ",C1) Processing '" + c0 + "' section block");
      
      Section s = new Section();

      if(lastSecContext == null)
      {
       if(lastSubmissionContext == null )
        sln.log(Level.ERROR, "(R" + lineNo + ",C1) Section must follow '" + SubmissionKeyword + "' block or other section block");
       else 
        lastSubmissionContext.getSubmission().setRootSection(s);
      }

      context = new SectionContext(s, this, sln, context);

      context.parseFirstLine(parts, lineNo);
      
      s.setType(c0);

      if(s.getAccNo() != null)
      {
       genAccNoMtch.reset( s.getAccNo() );
       
       SectionRef sr = new SectionRef(s);
       
       if( genAccNoMtch.matches() )
       {
        sr.setLocal(false);
        
        String pfx = genAccNoMtch.group("pfx");
        String sfx = genAccNoMtch.group("sfx");
        
        sr.setPrefix(pfx);
        sr.setSuffix(sfx);
        
        sr.setAccNo(genAccNoMtch.group("tmpid"));
        s.setAccNo(sr.getAccNo());
        
        boolean gen=false;
        
        if( pfx != null && pfx.length() > 0 )
        { 
         gen = true;
         
         if( Character.isDigit( pfx.charAt(pfx.length()-1) ) )
          sln.log(Level.ERROR, "(R" + lineNo + ",C2) Accession number prefix can't end with a digit '" + pfx + "'");
        }
        
        if( sfx != null && sfx.length() > 0 ) 
        { 
         gen = true;
         
         if( Character.isDigit( sfx.charAt(0) ) )
          sln.log(Level.ERROR, "(R" + lineNo + ",C2) Accession number suffix can't start with a digit '" + sfx + "'");
        }
        
        if( gen )
         submInf.addSec2genId(sr);
       }
       else
        s.setAccNo(null);

       
       if( sr.getAccNo() != null && sr.getAccNo().length() > 0 )
       {
        if(submInf.getSectionMap().containsKey(sr.getAccNo()))
         sln.log(Level.ERROR, "(R" + lineNo + ",C2) Section accession number '" + sr.getAccNo() + "' is arleady used for another object");
        else
         submInf.getSectionMap().put(sr.getAccNo(), sr);
       }
       
      }

      String pAcc = s.getParentAccNo();
      SectionContext pSecCtx = lastSecContext;
      
      if(pAcc != null && pAcc.length() > 0)
      {
       pSecCtx = secMap.get(pAcc);

       if(pSecCtx == null)
        sln.log(Level.ERROR, "(R" + lineNo + ",C3) Parent section '" + pAcc + "' not found");
       else if( context.getContextLogNode() != pSecCtx.getContextLogNode() )
       {
        sln.move(curCtx.getContextLogNode(), pSecCtx.getContextLogNode());
        
        context.setParentContext(pSecCtx);
       }
      }
      

      
      if( pSecCtx != null )
       pSecCtx.getSection().addSection(s);

      lastSecContext = (SectionContext)context;

      if( s.getAccNo() != null && s.getAccNo().length() > 0 )
      {
       secMap.put(s.getAccNo(), lastSecContext);
      }

     }
    }

   }
   else  // Some non-void context is active
   {
    context.parseLine(parts,lineNo);
   }

  }

  SimpleLogNode.setLevels(topLn);
  
  return res;
 }
 
 
 private void checkSubmRefs( SubmissionContext sctx )
 {
  sctx.getSubmission().getRootSection();
  
 }
 
 private void checkSecRefs( Section sec, Map<String,Section> secMap, LogNode ln )
 {
  if( sec.getAttributes() == null )
   return;
  
  for( SectionAttribute at : sec.getAttributes() )
  {
   if( ! at.isReference() )
    return;
   
   if( ! secMap.containsKey(at.getValue()) )
   {
    ln.log(Level.ERROR, "Invalid reference. Target doesn't exist: "+at.getValue());
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
