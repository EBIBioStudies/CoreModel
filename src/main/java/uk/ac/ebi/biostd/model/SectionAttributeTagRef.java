package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class SectionAttributeTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="attribute_id")
 @ForeignKey(name="attribute_fk")
 public SectionAttribute getAttribute()
 {
  return attribute;
 }
 private SectionAttribute attribute;

 public void setAttribute(SectionAttribute section)
 {
  this.attribute = section;
 }

}
