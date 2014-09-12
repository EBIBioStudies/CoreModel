package uk.ac.ebi.biostd.pagetab;

import java.io.File;
import java.io.IOException;

import uk.ac.ebi.biostd.model.Submission;
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

  PageTabSyntaxParser pars = new PageTabSyntaxParser();
  
  try
  {
   Submission sbm = pars.parse(text);
   
   System.out.println(sbm.getAcc());
   
  }
  catch(ParserException e)
  {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  
  
  
 }

}
