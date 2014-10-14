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
  File f = new File("e:/dev/temp.zip");

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
