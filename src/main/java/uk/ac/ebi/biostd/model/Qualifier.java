package uk.ac.ebi.biostd.model;

public class Qualifier implements NameValuePair
{
 private String name;

 private String value;
 
 public Qualifier()
 {}
 
 public Qualifier(String name, String value)
 {
  super();
  this.name = name;
  this.value = value;
 }

 @Override
 public String getName()
 {
  return name;
 }
 
 public void setName(String name)
 {
  this.name = name;
 }

 @Override
 public String getValue()
 {
  return value;
 }

 public void setValue(String value)
 {
  this.value = value;
 }
 
 @Override
 public String toString()
 {
  return name+">"+value;
 }
 
}
