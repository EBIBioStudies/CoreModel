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
@Table(name="link_attr")
public class LinkAttribute extends AbstractAttribute
{
 public LinkAttribute()
 {}
 
 public LinkAttribute(String name, String value)
 {
  super(name,value);
 }
 
 public LinkAttribute(String name, String value, String nameQual, String valQual)
 {
  super(name, value, nameQual, valQual);
 }
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="link_id")
 @ForeignKey(name="link_fk")
 public Link getHost()
 {
  return host;
 }
 private Link host;
 
 public void setHost( Link h )
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
