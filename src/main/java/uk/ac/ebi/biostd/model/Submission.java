package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.authz.User;

@Entity
public class Submission implements Annotated, SecurityObject, Classified
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

 @ManyToOne
 @JoinColumn(name="owner_id")
 public User getOwner()
 {
  return owner;
 }
 private User owner;

 public void setOwner(User owner)
 {
  this.owner = owner;
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
 @Transient
 public String getEntityClass()
 {
  if( getTagRefs() == null )
   return null;
  
  StringBuilder sb = new StringBuilder();
  
  for( TagRef t : getTagRefs() )
  {
   sb.append(t.getTag().getClassifier().getName()).append(":").append(t.getTag().getName());
   
   if( t.getParameter() != null && t.getParameter().length() != 0 )
    sb.append("=").append( t.getParameter() );
   
   sb.append(",");
  }
  
  if( sb.length() > 0 )
   sb.setLength( sb.length()-1 );
  
  return sb.toString();
 }
 
 
 @Override
 @OneToMany(mappedBy="submission",cascade=CascadeType.ALL)
 public Collection<SubmissionTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<SubmissionTagRef> tagRefs;

 public void setTagRefs(Collection<SubmissionTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 public void addTagRef( SubmissionTagRef tr )
 {
  if( tagRefs == null )
   tagRefs = new ArrayList<>();
   
  tagRefs.add(tr);
 }

 @Override
 @ManyToMany
 public Collection<AccessTag> getAccessTags()
 {
  return accessTags;
 }
 private Collection<AccessTag> accessTags;

 public void setAccessTags(Collection<AccessTag> accessTags)
 {
  this.accessTags = accessTags;
 }
 
 public void addAccessTags( AccessTag t )
 {
  if( accessTags == null )
   accessTags = new ArrayList<>();
   
  accessTags.add(t);
 }
}
