package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorColumn(name="ref", discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue(value="0")
public class LinkAttribute extends AbstractAttribute
{
 public LinkAttribute()
 {}
 
 public LinkAttribute(String name, String value)
 {
  super(name,value);
 }
 
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="link_id")
 public Link getHost()
 {
  return host;
 }
 private Link host;
 
 public void setHost( Link h )
 {
  host=h;
 }
 
 
 @Override
 @OneToMany(mappedBy="attribute",cascade=CascadeType.ALL)
 public Collection<LinkAttributeTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<LinkAttributeTagRef> tagRefs;

 public void setTagRefs(Collection<LinkAttributeTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 public void addTagRef( LinkAttributeTagRef tr )
 {
  if( tagRefs == null )
   tagRefs = new ArrayList<>();
   
  tagRefs.add(tr);
 }
}
