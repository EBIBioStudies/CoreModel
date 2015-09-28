package uk.ac.ebi.biostd.util;

import java.nio.file.Path;

public class FilePointer
{
 private Path archivePath;
 private Path fullPath;
 private Path relativePath;
 private String archiveInternalPath;
 private boolean directory;
 private long size;

 public Path getArchivePath()
 {
  return archivePath;
 }

 public void setArchivePath(Path archivePath)
 {
  this.archivePath = archivePath;
 }

 public Path getFullPath()
 {
  return fullPath;
 }

 public void setFullPath(Path fullPath)
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

 public Path getRelativePath()
 {
  return relativePath;
 }

 public void setRelativePath(Path relativePath)
 {
  this.relativePath = relativePath;
 }
 
 @Override
 public String toString()
 {
  return fullPath.toString();
 }

 public boolean isDirectory()
 {
  return directory;
 }

 public void setDirectory(boolean directory)
 {
  this.directory = directory;
 }

 public long getSize()
 {
  return size;
 }

 public void setSize(long size)
 {
  this.size = size;
 }
}
