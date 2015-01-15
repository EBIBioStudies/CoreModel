package uk.ac.ebi.biostd.pagetab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import uk.ac.ebi.biostd.export.PageMLFormatter;
import uk.ac.ebi.biostd.export.SubmissionPageMLFormatter;
import uk.ac.ebi.biostd.model.PreparedSubmission;
import uk.ac.ebi.biostd.model.SectionRef;
import uk.ac.ebi.biostd.treelog.ErrorCounter;
import uk.ac.ebi.biostd.treelog.ErrorCounterImpl;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;
import uk.ac.ebi.biostd.treelog.SimpleLogNode;
import uk.ac.ebi.biostd.util.FileUtil;

public class TestPageTab
{
 static class IdGen
 {
  private int gen=1;
  
  public int getId()
  {
   return gen++;
  }
 }
 
 /**
  * @param args
  * @throws IOException 
  */
 public static void main(String[] args) throws IOException
 {
  File in = new File("e:/dev/temp/dataUni_us.txt");
//  File in = new File("C:/Documents and Settings/Mike/My Documents/Upload/idgen.txt");

  FileInputStream fis = new FileInputStream(in);
  
  int first = fis.read();
  int second = fis.read();
  
  fis.close();
  
  Charset cs = Charset.defaultCharset();
  
  if( ( first == 0xFF && second == 0xFE ) || ( first == 0xFE && second == 0xFF ) )
   cs = Charset.forName("UTF-16");
  
  
  String text = FileUtil.readFile( in, cs );

  ParserConfig cfg = new ParserConfig();
  PageTabSyntaxParser2 pars = new PageTabSyntaxParser2( null, cfg );
  
  ErrorCounter cnt = new ErrorCounterImpl();
  
  SimpleLogNode ln = new SimpleLogNode(Level.SUCCESS, "Processing Page-Tab file", cnt);
  
  
  List<PreparedSubmission> sbm = null ;

  try
  {
   sbm = pars.parse(text,ln);
  }
  catch(ParserException e)
  {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
   

  if(ln.getLevel() == Level.SUCCESS)
  {
   IdGen idGen = new IdGen();

   
   for(PreparedSubmission s : sbm)
   {
    if(s.getAccPrefix() != null || s.getAccSuffix() != null)
     s.getSubmission().setAccNo(
       (s.getAccPrefix() != null ? s.getAccPrefix() : "") + idGen.getId() + (s.getAccSuffix() != null ? s.getAccSuffix() : ""));
    
    for( SectionRef sr : s.getGlobalSections() )
    {
      String accNo = (sr.getPrefix() != null ? sr.getPrefix() : "") + idGen.getId() + (sr.getSuffix() != null ? sr.getSuffix() : "");
      
      sr.getSection().setAccNo( accNo );
    }
   }
  }
  
//  System.out.println(sbm.getAcc());
  
  printNode(ln,"");
  
  if( ln.getLevel() == Level.SUCCESS )
  {

   File xmlOut = new File( in.getParentFile(), "xmlout.xml");
   
   PageMLFormatter fmt = new SubmissionPageMLFormatter();
   
   FileWriter out = new FileWriter(xmlOut);
   
   out.append("<data>\n");
   
   for( PreparedSubmission s : sbm )
    fmt.format(s.getSubmission(), out);
   
   out.append("</data>\n");
   
   out.close();
   
  }
  
 }

 private static void printNode(LogNode ln, String indent)
 {
  System.out.println(indent+ln.getLevel()+" "+ln.getMessage());
  
  if( ln.getSubNodes() == null )
   return;
  
  for( LogNode sln : ln.getSubNodes() )
   printNode(sln,indent+"   ");
  
 }

}
