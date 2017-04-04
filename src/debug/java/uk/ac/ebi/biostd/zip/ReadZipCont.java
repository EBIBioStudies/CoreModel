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

package uk.ac.ebi.biostd.zip;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ReadZipCont
{

 public static void main(String[] args) throws ZipException, IOException
 {
  File f = new File("c:/dev/tmp/tmp.zip");

  ZipFile zf = new ZipFile(f);
  
  printEntries(zf.entries()," ");
  
  zf.close();
  
 }
 
 static void printEntries( Enumeration<? extends ZipEntry> eset, String indent )
 {
  
  for( ZipEntry ze : Collections.list( eset ) )
  {
   System.out.print( indent + ze.getName() );
   
   if( ze.isDirectory() )
   {
    System.out.println(" DIR");
   }
   else
    System.out.println(" "+ze.getSize());
   
  }
  
 }

}
