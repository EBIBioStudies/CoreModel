package uk.ac.ebi.biostd.jpatest;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class BaseB implements Base
{
 private long id;

 @Override
 @Id
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
  return "BaseB: "+name;
 }
 String name;

 @Override
 public void setName(String nm)
 {
  name = nm;
 }
 
 @Override
 @OneToMany(mappedBy="base", cascade=CascadeType.ALL)
 public Collection<ResB> getRes()
 {
  return res;
 }
 private Collection<ResB> res;

 public void setRes(Collection<ResB> r)
 {
  res = r;  
 }
}
