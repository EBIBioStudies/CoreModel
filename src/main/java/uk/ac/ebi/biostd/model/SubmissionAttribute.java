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
@Table(name="submission_attr")
public class SubmissionAttribute extends AbstractAttribute
{
 public SubmissionAttribute()
 {}
 
 public SubmissionAttribute(String name, String value)
 {
  super(name,value);
 }
 
 public SubmissionAttribute(String name, String value, String nameQual, String valQual)
 {
  super(name, value, nameQual, valQual);
 }

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="submission_id")
 @ForeignKey(name="submission_fk")
 public Submission getHost()
 {
  return host;
 }
 private Submission host;
 
 public void setHost( Submission h )
 {
  host=h;
 }
 
 @Override
 @OneToMany(mappedBy="attribute",cascade=CascadeType.ALL, targetEntity=SubmissionAttributeTagRef.class)
 public Collection<SubmissionAttributeTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<SubmissionAttributeTagRef> tagRefs;

 public void setTagRefs(Collection<SubmissionAttributeTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 public void addTagRef( SubmissionAttributeTagRef tr )
 {
  if( tagRefs == null )
   tagRefs = new ArrayList<>();
   
  tagRefs.add(tr);
 }
}
