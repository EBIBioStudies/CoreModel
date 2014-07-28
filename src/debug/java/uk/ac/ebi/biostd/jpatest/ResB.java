package uk.ac.ebi.biostd.jpatest;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//@Entity
@DiscriminatorValue("ResB")
public class ResB extends Res
{
 private long id;

 @Id
 public long getId()
 {
  return id;
 }

 public void setId(long id)
 {
  this.id = id;
 }
 
 @Override
 @ManyToOne(targetEntity=BaseB.class)
 public Base getBase()
 {
  return base;
 }
 private Base base;

 @Override
 public void setBase(Base b)
 {
  base = b;
 }

 private String name;

 @Override
 public String getName()
 {
  return name;
 }

 @Override
 public void setName(String name)
 {
  this.name = name;
 }
 
}
