package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="sec_attr")
public class SectionAttribute extends AbstractAttribute
{
 public SectionAttribute()
 {}
 
 public SectionAttribute(String name, String value)
 {
  super(name,value);
 }
 
 public SectionAttribute(String name, String value, String nameQual, String valQual)
 {
  super(name, value, nameQual, valQual);
 }
 
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="section_id")
 @ForeignKey(name="section_fk")
 public Section getHost()
 {
  return host;
 }
 private Section host;
 
 public void setHost( Section h )
 {
  host=h;
 }
}
