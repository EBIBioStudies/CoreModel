package uk.ac.ebi.biostd.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class AbstractAttribute implements Classified
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
