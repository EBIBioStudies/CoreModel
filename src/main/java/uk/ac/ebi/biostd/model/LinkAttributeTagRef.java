package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class LinkAttributeTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="attribute_id")
 public LinkAttribute getAttribute()
 {
  return attribute;
 }
 private LinkAttribute attribute;

 public void setAttribute(LinkAttribute section)
 {
  this.attribute = section;
 }

}
