package uk.ac.ebi.biostd.authz;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ForeignKey;

@MappedSuperclass
public class TagACR
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
 @JoinColumn(name="tag_id")
 @ForeignKey(name="tag_fk")
 public Tag getTag()
 {
  return tag;
 }
 private Tag tag;

 public void setTag(Tag tag)
 {
  this.tag = tag;
 }
 
}
