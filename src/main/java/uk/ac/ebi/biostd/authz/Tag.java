package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
 @NamedQuery(name="Tag.getByName", query="SELECT t FROM Tag t LEFT JOIN t.classifier c where t.name=:tname AND c.name=:cname ")
})
@Table(
  indexes = {
     @Index(name = "name_idx", columnList = "name", unique=true)
  })
public class Tag
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
 
 
 
 @OneToMany(mappedBy="parentTag",cascade=CascadeType.ALL)
 public Collection<Tag> getSubTags()
 {
  return subTags;
 }
 private Collection<Tag> subTags;

 public void setSubTags(Collection<Tag> subTags)
 {
  this.subTags = subTags;
 }

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="parent_tag_id",foreignKey = @ForeignKey(name = "parent_tag_fk"))
 public Tag getParentTag()
 {
  return parentTag;
 }
 private Tag parentTag;
 
 public void setParentTag(Tag patentTag)
 {
  this.parentTag = patentTag;
 }
 
 
 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="classifier_id",foreignKey = @ForeignKey(name = "classifier_fk"))
 public Classifier getClassifier()
 {
  return classifier;
 }
 private Classifier classifier;

 public void setClassifier(Classifier classifier)
 {
  this.classifier = classifier;
 }
 
}
