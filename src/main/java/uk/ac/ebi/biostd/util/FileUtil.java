package uk.ac.ebi.biostd.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;




public class FileUtil
{

 public static String readFile( File f ) throws IOException
 {
  return readFile( f, Charset.defaultCharset() );
 }

 public static String readFile( File f, Charset chst ) throws IOException
 {
  FileInputStream fis = new FileInputStream(f);
  
  ByteArrayOutputStream baos = new ByteArrayOutputStream( (int)f.length() );
  try
  {
   byte[] buff = new byte[4096];
   int n;
   
   while( (n=fis.read(buff)) != -1 )
    baos.write(buff, 0, n);
   
   return new String(baos.toByteArray(),chst);
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
 
 public static String readUnicodeFile( File f ) throws IOException
 {
  FileInputStream fis = new FileInputStream(f);
  
  ByteArrayOutputStream baos = new ByteArrayOutputStream( (int)f.length() );
  try
  {
   byte[] buff = new byte[4096];
   int n;
   int read = 0;
   
   do
   {
    n=fis.read(buff,read,buff.length-read);
    
    if( n == -1 )
    {
     if( read == 0 )
      return "";
     
     return new String(buff,0,1);
    }
    
    read+=n;
   }
   while( read < 2 );
   
   Charset cs = null;
   
   if( ( buff[0] == 0xFF && buff[1] == 0xFE ) || ( buff[0] == 0xFE && buff[1] == 0xFF ) )
    cs = Charset.forName("UTF-16");
   else
    cs = Charset.forName("UTF-8");

   baos.write(buff, 0, read);
   
   while( (n=fis.read(buff)) != -1 )
    baos.write(buff, 0, n);
   
   return new String(baos.toByteArray(),cs);
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
