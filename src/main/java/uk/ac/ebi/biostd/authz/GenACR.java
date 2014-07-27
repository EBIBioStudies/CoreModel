package uk.ac.ebi.biostd.authz;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ForeignKey;

@MappedSuperclass
public class GenACR<T>
{
 @Id
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="host_id")
 @ForeignKey(name="host_fk")
 public T getHost()
 {
  return tag;
 }
 private T tag;

 public void setHost(T tag)
 {
  this.tag = tag;
 }
 
}
