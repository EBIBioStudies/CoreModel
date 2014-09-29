package uk.ac.ebi.biostd.pagetab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import uk.ac.ebi.biostd.export.PageMLFormatter;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.treelog.ErrorCounerImpl;
import uk.ac.ebi.biostd.treelog.ErrorCounter;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;
import uk.ac.ebi.biostd.treelog.SimpleLogNode;
import uk.ac.ebi.biostd.util.FileUtil;

public class TestPageTab
{

 /**
  * @param args
  * @throws IOException 
  */
 public static void main(String[] args) throws IOException
 {
  File in = new File("e:/dev/temp/data.txt");
  
  String text = FileUtil.readFile( in );

  ParserConfig cfg = new ParserConfig();
  PageTabSyntaxParser2 pars = new PageTabSyntaxParser2( null, cfg );
  
  ErrorCounter cnt = new ErrorCounerImpl();
  
  SimpleLogNode ln = new SimpleLogNode(Level.SUCCESS, "Processing Page-Tab file", cnt);
  
  
  List<Submission> sbm = pars.parse(text,ln);
   
//  System.out.println(sbm.getAcc());
  
  printNode(ln,"");
  
  if( ln.getLevel() == Level.SUCCESS )
  {
   File xmlOut = new File( in.getParentFile(), "xmlout.xml");
   
   PageMLFormatter fmt = new PageMLFormatter();
   
   FileWriter out = new FileWriter(xmlOut);
   
   out.append("<data>\n");
   
   for( Submission s : sbm )
    fmt.format(s, out);
   
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
