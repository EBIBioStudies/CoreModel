/**

Copyright 2014-2017 Functional Genomics Development Team, European Bioinformatics Institute 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author Mikhail Gostev <gostev@gmail.com>

**/

package uk.ac.ebi.biostd.accpattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.biostd.in.Parser;
import uk.ac.ebi.biostd.in.pagetab.PageTabElements;

public class AccPatTest
{

 public static final String GeneratedAccNoPattern = Parser.GeneratedAccNoRx;

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
   System.out.print(">");
   inp = in.readLine();
   
   mtch.reset(inp);
   
   if( mtch.matches() )
   {
    System.out.println("TmpAcc='"+mtch.group("tmpid")+"' Pfx='"+mtch.group("pfx")+"' Sfx='"+mtch.group("sfx")+"'");
   }
   
  }

  
 }

}
