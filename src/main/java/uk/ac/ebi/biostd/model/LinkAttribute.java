package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="link_attr")
public class LinkAttribute extends AbstractAttribute
{
 public LinkAttribute()
 {}
 
 public LinkAttribute(String name, String value)
 {
  super(name,value);
 }
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="link_id")
 @ForeignKey(name="link_fk")
 public Link getHost()
 {
  return host;
 }
 private Link host;
 
 public void setHost( Link h )
 {
  host=h;
 }
}
