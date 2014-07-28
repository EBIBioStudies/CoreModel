package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class SectionAttributeTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="attribute_id")
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
