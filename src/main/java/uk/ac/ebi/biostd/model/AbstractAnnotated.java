package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@MappedSuperclass
public abstract class  AbstractAnnotated<T extends AbstractAttribute>
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
 public List<T> getAttributes()
 {
  return attributes;
 }
 private List<T> attributes;

 public void addAttribute( T nd )
 {
  if( attributes == null )
   attributes = new ArrayList<T>();
  
  attributes.add(nd);
 }
 
 public void setAttributes( List<T> sn )
 {
  attributes = sn;
 }
}
