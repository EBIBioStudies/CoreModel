package uk.ac.ebi.biostd.accpattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.biostd.in.pagetab.PageTabElements;

public class AccPatTest
{

 public static final String GeneratedAccNoPattern = "(?<tmpid>[^{]+)?(?:\\{(?<pfx>[^,}]+)(?:,(?<sfx>[^}]+))?\\})?";

 public static void main(String[] args) throws IOException
 {
  System.out.println(Arrays.asList("a;b\\;c".split("(?<!\\\\);")));
  System.out.println("A: '"+"a".substring(1)+"'");
  
  String str = "[a]";
  
  System.out.println(str+" matches "+PageTabElements.ValueQualifierRx+" : "+Pattern.compile(PageTabElements.ValueQualifierRx).matcher(str).matches());

  str = "aa[ bb ]";
  
  System.out.println(str+" matches "+PageTabElements.TableBlockRx+" : "+Pattern.compile(PageTabElements.TableBlockRx).matcher(str).matches());

  
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
