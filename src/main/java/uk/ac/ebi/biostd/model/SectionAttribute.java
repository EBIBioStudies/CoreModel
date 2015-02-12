package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import uk.ac.ebi.biostd.authz.Tag;

@Entity
public class SectionAttribute extends AbstractAttribute
{
 public SectionAttribute()
 {
 }
 
 public SectionAttribute(String name, String value)
 {
  super(name,value);
 }
 
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="section_id")
 public Section getHost()
 {
  return host;
 }
 private Section host;
 
 public void setHost( Section h )
 {
  host=h;
 }
 
 
 @Override
 @OneToMany(mappedBy="attribute",cascade=CascadeType.ALL)
 public Collection<SectionAttributeTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<SectionAttributeTagRef> tagRefs;

 public void setTagRefs(Collection<SectionAttributeTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 @Override
 public SectionAttributeTagRef addTagRef( Tag t, String val )
 {
  SectionAttributeTagRef ftr = new SectionAttributeTagRef();
  
  ftr.setTag(t);
  ftr.setParameter(val);
  
  addTagRef(ftr);
  
  return ftr;
 }
 
 public void addTagRef( SectionAttributeTagRef tr )
 {
  if( tagRefs == null )
   tagRefs = new ArrayList<>();
   
  tagRefs.add(tr);
 }

}
