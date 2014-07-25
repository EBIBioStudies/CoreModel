package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="file_attr")
public class FileAttribute extends AbstractAttribute
{
 public FileAttribute()
 {}
 
 public FileAttribute(String name, String value)
 {
  super(name,value);
 }
 
 public FileAttribute(String name, String value, String nameQual, String valQual)
 {
  super(name, value, nameQual, valQual);
 }
 
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="file_id")
 @ForeignKey(name="file_fk")
 public FileRef getHost()
 {
  return host;
 }
 private FileRef host;
 
 public void setHost( FileRef h )
 {
  host=h;
 }
 
 @OneToMany(mappedBy="attribute",cascade=CascadeType.ALL)
 public Collection<FileAttributeTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<FileAttributeTagRef> tagRefs;

 public void setTagRefs(Collection<FileAttributeTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 public void addTagRef( FileAttributeTagRef tr )
 {
  if( tagRefs == null )
   tagRefs = new ArrayList<>();
   
  tagRefs.add(tr);
 }
}
