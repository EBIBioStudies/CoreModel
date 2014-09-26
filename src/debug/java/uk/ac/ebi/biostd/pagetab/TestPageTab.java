package uk.ac.ebi.biostd.pagetab;

import java.io.File;
import java.io.IOException;

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
  String text = FileUtil.readFile(new File("e:/dev/temp/pagetab.txt") );

  ParserConfig cfg = new ParserConfig();
  PageTabSyntaxParser2 pars = new PageTabSyntaxParser2( null, cfg );
  
  ErrorCounter cnt = new ErrorCounerImpl();
  
  SimpleLogNode ln = new SimpleLogNode(Level.SUCCESS, "", cnt);
  
  
  Submission sbm = pars.parse(text,ln);
   
  System.out.println(sbm.getAcc());
  
  printNode(ln,"");
  
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
