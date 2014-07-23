package uk.ac.ebi.biostd.authz;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

abstract public class TagRef
{

 @ManyToOne
 @JoinColumn(name="tag_id")
 @ForeignKey(name="tag_fk")
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
