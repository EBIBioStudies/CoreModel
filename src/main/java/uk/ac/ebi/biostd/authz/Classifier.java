package uk.ac.ebi.biostd.authz;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Classifier
{
 
 public Classifier()
 {}
 
 @Id
 @GeneratedValue
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

 @OneToMany(mappedBy="classifier",cascade=CascadeType.ALL)
 public Collection<Tag> getTags()
 {
  return tags;
 }
 private Collection<Tag> tags = new ArrayList<Tag>();
 
 public void setTags(Collection<Tag> tags)
 {
  this.tags = tags;
 }

 @Transient
 public Tag getTag(String tagId)
 {
  for(Tag t : tags)
  {
   if( t.getName().equals(tagId))
    return t;
  }
  
  return null;
 }

 public void addTag(Tag tb)
 {
  tags.add(tb);
 }

}
