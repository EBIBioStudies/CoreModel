package uk.ac.ebi.biostd.authz;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class TagRef
{

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }

 @ManyToOne
 @JoinColumn(name="tag_id")
 public Tag getTag()
 {
  return tag;
 }
 private Tag    tag;

 public void setTag(Tag tag)
 {
  this.tag = tag;
 }

 public String getParameter()
 {
  return parameter;
 }
 private String parameter;

 public void setParameter(String parameter)
 {
  this.parameter = parameter;
 }
}
