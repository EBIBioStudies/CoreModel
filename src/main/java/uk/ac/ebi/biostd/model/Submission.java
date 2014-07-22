package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

import uk.ac.ebi.biostd.authz.Tag;

@Entity
public class Submission implements Annotated, Classified, Tagged, SecurityObject
{
 @Id
 @GeneratedValue
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }
 
 public String getAcc()
 {
  return acc;
 }
 private String acc;

 public void setAcc(String acc)
 {
  this.acc = acc;
 }

 @Override
 @OneToMany(mappedBy="host",cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 public List<SubmissionAttribute> getAttributes()
 {
  return attributes;
 }
 private List<SubmissionAttribute> attributes;

 public void addAttribute( SubmissionAttribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<SubmissionAttribute>();
  
  attributes.add(nd);
  nd.setHost(this);
 }
 
 public void setAttributes( List<SubmissionAttribute> sn )
 {
  attributes = sn;
  
  for(SubmissionAttribute sa : sn )
   sa.setHost(this);
 }
 
 
 @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
 @ForeignKey(name="sec_fk")
 @NotNull
 public Section getRootSection()
 {
  return rootSection;
 }
 private Section rootSection;
 
 public void setRootSection(Section rootSection)
 {
  this.rootSection = rootSection;
  
  rootSection.setSubmission(this);
 }

 @Override
 public AbstractAttribute addAttribute(String name, String value)
 {
  return addAttribute(name, value, null, null);
 }

 @Override
 public AbstractAttribute addAttribute(String name, String value, String nameQual, String valQual)
 {
  SubmissionAttribute sa = new SubmissionAttribute( name, value, nameQual, valQual );
  
  addAttribute(sa);
  
  return sa;
 }
 
 @Override
 public String getEntityClass()
 {
  return entityClass;
 }
 private String entityClass;
 
 @Override
 public void setEntityClass( String cls )
 {
  entityClass = cls;
 }

 
 @Override
 public Collection<Tag> getTags()
 {
  return tags;
 }
 private Collection<Tag> tags;

 @Override
 public void setTags(Collection<Tag> tags)
 {
  this.tags = tags;
 }

 @Override
 @Transient
 public String getAccessTags()
 {
  return accessTags;
 }
 
}
