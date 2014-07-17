package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.ForeignKey;

@Entity
public class Section implements Annotated, Classified
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

 public String getParentAcc()
 {
  return parentAcc;
 }
 private String parentAcc;
 
 public void setParentAcc( String pa )
 {
  parentAcc = pa;
 }
 
 @OneToMany(mappedBy="host",cascade=CascadeType.ALL)
 @OrderColumn(name="ord",insertable=true)
 public List<SectionAttribute> getAttributes()
 {
  return attributes;
 }
 private List<SectionAttribute> attributes;

 public void addAttribute( SectionAttribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<SectionAttribute>();
  
  attributes.add(nd);
  nd.setHost(this);
 }
 
 public void setAttributes( List<SectionAttribute> sn )
 {
  attributes = sn;
  
  for(SectionAttribute sa : sn )
   sa.setHost(this);
 }
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="submission_id")
 @ForeignKey(name="submission_fk")
 public Submission getSubmission()
 {
  return submission;
 }
 private Submission submission;
 
 public void setSubmission(Submission study)
 {
  this.submission = study;
  
  if( sections != null )
  {
   for(Section s : sections )
    s.setParentSection(this);
  }
 }

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="parent_id")
 @ForeignKey(name="section_fk")
 public Section getParentSection()
 {
  return parentNode;
 }
 private Section parentNode;
 
 public void setParentSection( Section pr )
 {
  parentNode = pr;
  
  if( pr == null )
   return;

  parentAcc = pr.getAcc();

  setSubmission(pr.getSubmission());
  
  if( sections != null )
  {
   for(Section s : sections )
    s.setParentSection(this);
  }
 }
 
 public String getType()
 {
  return type;
 }
 private String type;

 public void setType(String type)
 {
  this.type = type;
 }
 
 public String getAcc()
 {
  return acc;
 }
 private String acc;

 public void setAcc(String acc)
 {
  this.acc = acc;

  if( sections != null )
  {
   for(Section s : sections )
    s.setParentAcc(acc);
  }
 }
 
 @OneToMany(mappedBy="parentSection",cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 public List<Section> getSections()
 {
  return sections;
 }
 private List<Section> sections;

 public void addSection( Section nd )
 {
  if( sections == null )
   sections = new ArrayList<Section>();
  
  sections.add(nd);
  
  nd.setParentSection(this);
 }
 
 public void setSections( List<Section> sn )
 {
  sections = sn;
  
  if( sn == null )
   return;
  
  for(Section s : sn )
   s.setParentSection(this);
 }
 
 
 @OneToMany(mappedBy="hostSection",cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 public List<FileRef> getFileRefs()
 {
  return fileRefs;
 }
 private List<FileRef> fileRefs;

 public void addFileRef( FileRef nd )
 {
  if( fileRefs == null )
   fileRefs = new ArrayList<FileRef>();
  
  fileRefs.add(nd);
  
  nd.setHostSection(this);
 }
 
 public void setFileRefs( List<FileRef> sn )
 {
  fileRefs = sn;

  if( sn == null )
   return;
  
  for(FileRef s : sn )
   s.setHostSection(this);

 }
 
 @OneToMany(mappedBy="hostSection",cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 public List<Link> getLinks()
 {
  return links;
 }
 private List<Link> links;

 public void addLink( Link nd )
 {
  if( links == null )
   links = new ArrayList<Link>();
  
  links.add(nd);
  nd.setHostSection(this);
 }
 
 public void setLinks( List<Link> sn )
 {
  links = sn;
 
  if( sn == null )
   return;
  
  for(Link s : sn )
   s.setHostSection(this);

 }

 @Override
 public AbstractAttribute addAttribute(String name, String value)
 {
  return addAttribute(name, value, null, null);
 }

 @Override
 public AbstractAttribute addAttribute(String name, String value, String nameQual, String valQual)
 {
  SectionAttribute sa = new SectionAttribute( name, value, nameQual, valQual );
  
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
}
