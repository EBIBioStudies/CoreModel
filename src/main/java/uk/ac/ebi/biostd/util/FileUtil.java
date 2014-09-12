package uk.ac.ebi.biostd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class FileUtil
{

 public static String readFile( File f ) throws IOException
 {
  return readFile( f, Charset.defaultCharset() );
 }

 public static String readFile( File f, Charset chst ) throws IOException
 {
  FileInputStream fis = new FileInputStream(f);
  
  ByteOutputStream baos = new ByteOutputStream( (int)f.length() );
  try
  {
   byte[] buff = new byte[4096];
   int n;
   
   while( (n=fis.read(buff)) != -1 )
    baos.write(buff, 0, n);
   
   return new String(baos.getBytes(),chst);
  }
  finally
  {
   if( fis != null )
   {
    try
    {
     fis.close();
    }
    catch(Exception e)
    {
    }
   }
   
   baos.close();
  }
  
 }
 
}
