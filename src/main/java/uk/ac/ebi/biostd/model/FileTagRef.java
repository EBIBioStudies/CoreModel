package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class FileTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="file_id")
 public FileRef getFileRef()
 {
  return fileRef;
 }
 private FileRef fileRef;

 public void setFileRef(FileRef fileRef)
 {
  this.fileRef = fileRef;
 }
//
// public void setSubmission(FileRef fileRef)
// {
//  this.fileRef = fileRef;
// }
}
