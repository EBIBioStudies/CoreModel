package uk.ac.ebi.biostd.util;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipPathCheck
{
 public static final String pathSeparator = "/";
 
 
 private File rootPath;
 
 public ZipPathCheck( File rp )
 {
  rootPath = rp;
 }

 
 public boolean checkPath( String path )
 {
  if( path.length() == 0 )
   return false;
  
  if( path.startsWith(pathSeparator) || path.charAt(0) == File.pathSeparatorChar )
   path = path.substring(1);

  String[] parts = path.split(pathSeparator);
  
  File cPath = rootPath;
  
  for( int i=0; i < parts.length; i++ )
  {
   if( "..".equals(parts[i]) )
    return false;
   
   cPath = new File( cPath, parts[i] );
   
   if( ! cPath.exists() )
    return false;
   
   if( ! cPath.isDirectory() )
   {
    if( i == parts.length-1 )
     return true;
    
    if( parts[i].length() > 4 && parts[i].substring(parts[i].length()-4).equalsIgnoreCase(".zip") )
     return checkZipPath(cPath, parts, i+1);
    
    return false;
   }
   
  }
  
  return false;
 }


 private boolean checkZipPath(File cPath, String[] parts, int start)
 {
  
  String fn = parts[ start ];
  
  if( start < parts.length -1 )
  {
   StringBuilder sb = new StringBuilder();
   
   for( int i=start; i < parts.length; i++ )
    sb.append(parts[i]).append('/');
   
   sb.setLength( sb.length()-1);
   
   fn = sb.toString();
  }
  
  try ( ZipFile zf = new ZipFile(cPath) )
  {

   Enumeration<? extends ZipEntry> eset = zf.entries();
   
   while( eset.hasMoreElements() )
   {
    String ze = eset.nextElement().getName();
    
    if( ze.equals(fn) )
     return true;
   }
   
  }
  catch(Exception e)
  {
  }
  
  
  return false;
 }
 
}
