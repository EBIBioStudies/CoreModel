package uk.ac.ebi.biostd.pagetab;

import java.io.File;
import java.io.IOException;

import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.treelog.ErrorCounerImpl;
import uk.ac.ebi.biostd.treelog.ErrorCounter;
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

  PageTabSyntaxParser pars = new PageTabSyntaxParser( null );
  
  ErrorCounter cnt = new ErrorCounerImpl();
  
  SimpleLogNode ln = new SimpleLogNode(Level.SUCCESS, "", cnt);
  
  ParserConfig cfg = new ParserConfig();
  
  Submission sbm = pars.parse(text,cfg,ln);
   
  System.out.println(sbm.getAcc());
  
 }

}
