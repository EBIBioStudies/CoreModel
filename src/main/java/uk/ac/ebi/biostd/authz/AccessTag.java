package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ForeignKey;

@Entity
public class AccessTag extends ACL
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

 public String getName()
 {
  return name;
 }

 private String name;

 public void setName(String name)
 {
  this.name = name;
 }

 public String getDescription()
 {
  return description;
 }

 private String description;

 public void setDescription(String description)
 {
  this.description = description;
 }

 @OneToMany(mappedBy = "parentTag", cascade = CascadeType.ALL)
 public Collection<Tag> getSubTags()
 {
  return subTags;
 }

 private Collection<Tag> subTags;

 public void setSubTags(Collection<Tag> subTags)
 {
  this.subTags = subTags;
 }

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "parent_tag_id")
 @ForeignKey(name = "parent_tag_fk")
 public Tag getParentTag()
 {
  return parentTag;
 }

 private Tag parentTag;

 public void setParentTag(Tag patentTag)
 {
  this.parentTag = patentTag;
 }

}

