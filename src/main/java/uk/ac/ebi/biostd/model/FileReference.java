package uk.ac.ebi.biostd.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="1")
public class FileReference extends FileAttribute
{
 public FileReference()
 {
  super();
 }
 
 public FileReference(String name, String value)
 {
  super(name,value);
 }
 
 @Override
 @Transient
 public boolean isReference()
 {
  return true;
 }
}
