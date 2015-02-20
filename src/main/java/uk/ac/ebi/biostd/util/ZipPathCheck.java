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

 
 public FilePointer checkPath( String path )
 {
  if( path.length() == 0 )
   return null;
  
  if( path.startsWith(pathSeparator) || path.charAt(0) == File.pathSeparatorChar )
   path = path.substring(1);

  String[] parts = path.split(pathSeparator);
  
  File cPath = rootPath;
  
  for( int i=0; i < parts.length; i++ )
  {
   if( "..".equals(parts[i]) )
    return null;
   
   cPath = new File( cPath, parts[i] );
   
   if( ! cPath.exists() )
    return null;
   
   if( ! cPath.isDirectory() )
   {
    if( i == parts.length-1 )
    {
     FilePointer fp = new FilePointer();
     
     fp.setFullPath(cPath.getAbsolutePath());
     fp.setRelativePath(path);
     
     return fp;
    }
    
    if( parts[i].length() > 4 && parts[i].substring(parts[i].length()-4).equalsIgnoreCase(".zip") )
    {
     FilePointer fp = checkZipPath(cPath, parts, i+1);
     fp.setRelativePath(path);

     return fp;
    }
    
    return null;
   }
   
  }
  
  return null;
 }


 private FilePointer checkZipPath(File cPath, String[] parts, int start)
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
    {
     FilePointer fp = new FilePointer();
     
     fp.setArchivePath(cPath.getAbsolutePath());
     fp.setArchiveInternalPath(fn);
     fp.setFullPath(fp.getArchivePath()+"/"+fn);
     
     return fp;
    }
   }
   
  }
  catch(Exception e)
  {
  }
  
  
  return null;
 }
 
}
