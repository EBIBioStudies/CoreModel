package uk.ac.ebi.biostd.zip;

import java.io.File;

import uk.ac.ebi.biostd.util.ZipPathCheck;

public class PathCheck
{

 public static void main(String[] args)
 {
  ZipPathCheck zp = new ZipPathCheck( new File("e:/dev/temp") );

  System.out.println("aaa.txt: "+zp.checkPath("aaa.txt"));
  System.out.println("xmlout.xml: "+zp.checkPath("xmlout.xml"));
  System.out.println("temp.zip: "+zp.checkPath("temp.zip"));
  System.out.println("temp.zip/temp: "+zp.checkPath("temp.zip/temp"));
  System.out.println("temp.zip/temp/data.txt: "+zp.checkPath("temp.zip/temp/data.txt"));
  
 }

}
