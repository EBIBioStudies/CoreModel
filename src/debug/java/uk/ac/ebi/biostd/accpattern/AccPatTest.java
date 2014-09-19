package uk.ac.ebi.biostd.accpattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccPatTest
{

 public static final String GeneratedAccNoPattern = "(?<tmpid>[^{]+)?(?:\\{(?<pfx>[^,}]+)(?:,(?<sfx>[^}]+))?\\})?";

 public static void main(String[] args) throws IOException
 {
  Pattern pat = Pattern.compile(GeneratedAccNoPattern);
  
  Matcher mtch = pat.matcher("");
  
  String inp = null;
  
  BufferedReader in = new BufferedReader(new InputStreamReader(System.in) );
  
  while( true )
  {
   inp = in.readLine();
   
   mtch.reset(inp);
   
   if( mtch.matches() )
   {
    System.out.println("TmpAcc="+mtch.group("tmpid")+" Pfx="+mtch.group("pfx")+" Sfx="+mtch.group("sfx"));
   }
   
  }

 }

}
