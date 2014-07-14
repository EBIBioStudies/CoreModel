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
public class Link
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
 public List<LinkAttribute> getAttributes()
 {
  return attributes;
 }
 private List<LinkAttribute> attributes;

 public void addAttribute( LinkAttribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<LinkAttribute>();
  
  attributes.add(nd);
  nd.setHost(this);
 }
 
 public void setAttributes( List<LinkAttribute> sn )
 {
  attributes = sn;
  
  for(LinkAttribute sa : sn )
   sa.setHost(this);
 }

 
 public String getUrl()
 {
  return url;
 }
 private String url;

 public void setUrl(String url)
 {
  this.url = url;
 }
 
 public boolean isLocal()
 {
  return local;
 }
 private boolean local;

 public void setLocal(boolean local)
 {
  this.local = local;
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
}
