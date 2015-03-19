package uk.ac.ebi.biostd.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
   byte[] buff = new byte[64*1024];
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
 
 public static byte[] readBinFile( File f ) throws IOException
 {
  FileInputStream fis = new FileInputStream(f);
  
  ByteArrayOutputStream baos = new ByteArrayOutputStream( (int)f.length() );
  try
  {
   byte[] buff = new byte[64*1024];
   int n;
   
   while( (n=fis.read(buff)) != -1 )
    baos.write(buff, 0, n);
   
   return baos.toByteArray();
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
 
 public static void copyFile( File inf, File outf ) throws IOException
 {
  byte[] buf = new byte[64*1024];
  
  try (
   InputStream fis = new FileInputStream(inf);
   OutputStream fos = new FileOutputStream(outf);
  )
  {
  int nread;
  while( (nread=fis.read(buf) ) > 0 )
   fos.write(buf, 0, nread);
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
   while( read < 3 );
   
   Charset cs = null;
   
   int offs = 0;
   
   if( ( buff[0] == (byte)0xFF && buff[1] == (byte)0xFE ) || ( buff[0] == (byte)0xFE && buff[1] == (byte)0xFF ) )
    cs = Charset.forName("UTF-16");
   else if( buff[0] == (byte)0xEF && buff[1] == (byte)0xBB && buff[2] == (byte)0xBF )
   {
    cs = Charset.forName("UTF-8");
    offs = 3;
   }
   else
    cs = Charset.forName("UTF-8");

   baos.write(buff, offs, read-offs);
   
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
