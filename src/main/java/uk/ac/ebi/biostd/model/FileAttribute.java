package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="file_attr")
public class FileAttribute extends AbstractAttribute
{
 public FileAttribute()
 {}
 
 public FileAttribute(String name, String value)
 {
  super(name,value);
 }
 
  
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="file_id")
 @ForeignKey(name="file_fk")
 public FileRef getHost()
 {
  return host;
 }
 private FileRef host;
 
 public void setHost( FileRef h )
 {
  host=h;
 }
}
