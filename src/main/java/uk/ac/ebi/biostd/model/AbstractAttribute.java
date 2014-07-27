package uk.ac.ebi.biostd.model;

import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import uk.ac.ebi.biostd.authz.TagRef;

@MappedSuperclass
abstract public class AbstractAttribute
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

 @Transient
 public abstract Collection<? extends TagRef> getTagRefs();
 
 @Transient
 public String getEntityClass()
 {
  if( getTagRefs() == null )
   return null;
  
  StringBuilder sb = new StringBuilder();
  
  for( TagRef t : getTagRefs() )
  {
   sb.append(t.getTag().getClassifier().getName()).append(":").append(t.getTag().getName());
   
   if( t.getParameter() != null && t.getParameter().length() != 0 )
    sb.append("=").append( t.getParameter() );
   
   sb.append(",");
  }
  
  if( sb.length() > 0 )
   sb.setLength( sb.length()-1 );
  
  return sb.toString();
 }
}
