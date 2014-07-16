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
public class FileRef implements Annotated
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
 
 @OneToMany(mappedBy="host",cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 @ForeignKey(name="section_fk")
 public List<FileAttribute> getAttributes()
 {
  return attributes;
 }
 private List<FileAttribute> attributes;

 public void addAttribute( FileAttribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<FileAttribute>();
  
  attributes.add(nd);
  nd.setHost(this);
 }
 
 public void setAttributes( List<FileAttribute> sn )
 {
  attributes = sn;
  
  for(FileAttribute sa : sn )
   sa.setHost(this);
 }
 
 public String getName()
 {
  return name;
 }
 private String name;

 public void setName(String name)
 {
  this.name = name;
 }
 
 @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
 @JoinColumn(name="sectionId")
 public Section getHostSection()
 {
  return hostSection;
 }
 private Section hostSection;
 
 public void setHostSection( Section pr )
 {
  hostSection = pr;
 }
 
 
 @Override
 public AbstractAttribute addAttribute(String name, String value)
 {
  return addAttribute(name, value, null, null);
 }

 @Override
 public AbstractAttribute addAttribute(String name, String value, String nameQual, String valQual)
 {
  FileAttribute sa = new FileAttribute( name, value, nameQual, valQual );
  
  addAttribute(sa);
  
  return sa;
 }
 
}
