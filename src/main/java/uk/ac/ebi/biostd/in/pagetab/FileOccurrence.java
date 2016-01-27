package uk.ac.ebi.biostd.in.pagetab;

import uk.ac.ebi.biostd.in.ElementPointer;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.util.FilePointer;

public class FileOccurrence
{


 private ElementPointer elementPointer;
 
 private FileRef fileRef;
 
 private LogNode secLogNode;
 
 private FilePointer filePointer;
 
 public ElementPointer getElementPointer()
 {
  return elementPointer;
 }

 public void setElementPointer(ElementPointer elementPointer)
 {
  this.elementPointer = elementPointer;
 }

 public FileRef getFileRef()
 {
  return fileRef;
 }

 public void setFileRef(FileRef fr)
 {
  this.fileRef = fr;
 }

 public LogNode getLogNode()
 {
  return secLogNode;
 }

 public void setLogNode(LogNode secLogNode)
 {
  this.secLogNode = secLogNode;
 }

 public FilePointer getFilePointer()
 {
  return filePointer;
 }

 public void setFilePointer(FilePointer filePointer)
 {
  this.filePointer = filePointer;
 }
 
 @Override
 public boolean equals( Object o )
 {
  if( o== null || ! (o instanceof FileOccurrence) )
   return false;
  
  FileRef fr2 = ((FileOccurrence)o).getFileRef();
  
  if( fileRef == null && fr2 == null )
   return true;
  
  if( fileRef != null )
  {
   if( fr2 == null )
    return false;
   
   if( fileRef.getName() == null && fr2.getName() == null )
    return true;
   
   if( fileRef.getName() != null )
    return fileRef.getName().equals( fr2.getName() ); 
  }
  else
   return fr2 == null;
  
  return false;
 }

 @Override
 public int hashCode()
 {
  if( fileRef == null || fileRef.getName() == null )
   return 0;
  
  return fileRef.getName().hashCode();
 }
}
