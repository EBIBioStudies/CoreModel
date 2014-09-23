package uk.ac.ebi.biostd.pagetab;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.trfactory.SubmissionTagRefFactory;
import uk.ac.ebi.biostd.pagetab.context.BlockContext;
import uk.ac.ebi.biostd.pagetab.context.BlockContext.BlockType;
import uk.ac.ebi.biostd.pagetab.context.SubmissionContext;
import uk.ac.ebi.biostd.pagetab.context.VoidContext;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

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
 public static final String TableBlockPattern     = "(?<name>[^[]+)[table]";

 public static final String SubmissionKeyword     = "Submission";
 public static final String FileKeyword           = "File";
 public static final String LinkKeyword           = "Link";

 private final TagResolver  tagRslv;

 private final Matcher      nameQualMtch;
 private final Matcher      valueQualMtch;

 public PageTabSyntaxParser2(TagResolver tr)
 {
  tagRslv = tr;

  nameQualMtch = Pattern.compile(NameQualifierPattern).matcher("");
  valueQualMtch = Pattern.compile(ValueQualifierPattern).matcher("");

 }

 public Submission parse( String txt, ParserConfig pConf, LogNode ln ) //throws ParserException
 {
  Submission subm = null;
  
  SpreadsheetReader reader = new SpreadsheetReader(txt);

  BlockContext context = new VoidContext();
  
  List<String> parts = new ArrayList<String>(100);

  int lineNo = 0;
  
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
    
    if( c0.equals(SubmissionKeyword) )
    {
     LogNode sln = ln.branch("(R"+lineNo+",C1) Processing '"+SubmissionKeyword+"' block");
     
     if( subm != null )
      sln.log(Level.ERROR, "(R"+lineNo+",C1) Multiple blocks: '"+SubmissionKeyword+"' are not allowed");
     
     subm = new Submission();
     
     context = new SubmissionContext( subm, parts, sln );
     
     if( sz2 > 0 )
     {
      String acc = cells2.get(0).getValue().trim();
      
      if( acc.length() > 0 )
       subm.setAcc( cells2.get(0).getValue() );
      else
       sln.log(Level.ERROR, "(R"+cells2.get(0).getRow()+",C"+cells2.get(0).getCol()+") Missing submission ID");
  
      subm.setAccessTags( processAccessTags(cells3,0,pConf,sln) );
      subm.setTagRefs( processTags(cells4,0,pConf,SubmissionTagRefFactory.getInstance(),sln) );

     }
     
     context = new SubmissionContext( subm );
     ctxLN = sln;

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
 
}
