package uk.ac.ebi.biostd.util;

public class FilePointer
{
 private String archivePath;
 private String fullPath;
 private String archiveInternalPath;

 public String getArchivePath()
 {
  return archivePath;
 }

 public void setArchivePath(String archivePath)
 {
  this.archivePath = archivePath;
 }

 public String getFullPath()
 {
  return fullPath;
 }

 public void setFullPath(String fullPath)
 {
  this.fullPath = fullPath;
 }

 public String getArchiveInternalPath()
 {
  return archiveInternalPath;
 }

 public void setArchiveInternalPath(String archiveInternalPath)
 {
  this.archiveInternalPath = archiveInternalPath;
 }
}
