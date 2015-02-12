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

}
