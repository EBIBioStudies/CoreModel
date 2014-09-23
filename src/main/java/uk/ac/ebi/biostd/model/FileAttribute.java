package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class FileAttribute extends AbstractAttribute
{
 public FileAttribute()
 {}
 
 public FileAttribute(String name, String value)
 {
  super(name,value);
 }
 

 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="file_id")
 public FileRef getHost()
 {
  return host;
 }
 private FileRef host;
 
 public void setHost( FileRef h )
 {
  host=h;
 }
 
 @Override
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
