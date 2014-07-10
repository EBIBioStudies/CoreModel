package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@MappedSuperclass
public abstract class AbstractAttributed
{
 @Id
 @GeneratedValue
 public long getId()
 {
  return id;
 }
 private long id;
 
 @OneToMany
 @OrderColumn(name="index")
 public List<Attribute> getAttributes()
 {
  return attributes;
 }
 private List<Attribute> attributes;

 public void addAttribute( Attribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<Attribute>();
  
  attributes.add(nd);
 }
 
 public void setAttributes( List<Attribute> sn )
 {
  attributes = sn;
 }
}
