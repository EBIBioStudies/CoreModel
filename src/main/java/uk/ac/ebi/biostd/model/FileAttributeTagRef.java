package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class FileAttributeTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="attribute_id")
 @ForeignKey(name="attribute_fk")
 public FileAttribute getAttribute()
 {
  return attribute;
 }
 private FileAttribute attribute;

 public void setAttribute(FileAttribute fileRef)
 {
  this.attribute = fileRef;
 }
}
