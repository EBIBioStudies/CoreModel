package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import uk.ac.ebi.biostd.authz.AccessTag;

@MappedSuperclass
abstract public class AbstractAttribute implements SecurityObject
{
 public AbstractAttribute()
 {}

 public AbstractAttribute(String name2, String value2)
 {
  setName(name2);
  setValue(value2);
 }
 
 public AbstractAttribute(String name2, String value2, String nameQual, String valueQual )
 {
  setName(name2);
  setValue(value2);
  setNameQualifier(nameQual);
  setValueQualifier(valueQual);
 }

 
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

 public String getName()
 {
  return name;
 }
 String name;

 public void setName(String name)
 {
  this.name = name;
 }
 
 public String getNameQualifier()
 {
  return nameQualifier;
 }
 String nameQualifier;

 public void setNameQualifier(String name)
 {
  this.nameQualifier = name;
 }

 
 public String getValue()
 {
  return value;
 }
 String value;

 public void setValue(String value)
 {
  this.value = value;
 }

 public String getValueQualifier()
 {
  return valueQualifier;
 }
 String valueQualifier;

 public void setValueQualifier(String value)
 {
  this.valueQualifier = value;
 }

 
 public double getNumValue()
 {
  return numValue;
 }
 double numValue;

 public void setNumValue(double numValue)
 {
  this.numValue = numValue;
 }
 
 

 public String getAttrClass()
 {
  return attrClass;
 }
 String attrClass;

 public void setAttrClass(String attrClass)
 {
  this.attrClass = attrClass;
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
