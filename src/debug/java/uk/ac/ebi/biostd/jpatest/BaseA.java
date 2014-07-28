package uk.ac.ebi.biostd.jpatest;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class BaseA implements Base
{
 
 private long id;

 @Id
 @Override
 public long getId()
 {
  return id;
 }

 @Override
 public void setId(long id)
 {
  this.id = id;
 }

 @Override
 public String getName()
 {
  return "BaseA: "+name;
 }
 String name;

 @Override
 public void setName(String nm)
 {
  name = nm;
 }

 @Override
 @OneToMany(mappedBy="base", cascade=CascadeType.ALL)
 public Collection<ResA> getRes()
 {
  return res;
 }
 private Collection<ResA> res;

 public void setRes(Collection<ResA> r)
 {
  res = r;  
 }

}
