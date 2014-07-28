package uk.ac.ebi.biostd.jpatest;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//@Entity
@DiscriminatorValue("ResA")
public class ResA extends Res
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
 @ManyToOne(targetEntity=BaseA.class)
 public BaseA getBase()
 {
  return base;
 }
 private BaseA base;

 public void setBase(BaseA b)
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

 @Override
 public void setBase(Base b)
 {
  setBase((BaseA)b);
 }
 
}
