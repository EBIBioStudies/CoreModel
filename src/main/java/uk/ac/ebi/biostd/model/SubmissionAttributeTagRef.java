package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class SubmissionAttributeTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="attribute_id")
 @ForeignKey(name="attribute_fk")
 public SubmissionAttribute getAttribute()
 {
  return attribute;
 }
 private SubmissionAttribute attribute;

 public void setAttribute(SubmissionAttribute submission)
 {
  this.attribute = submission;
 }
}
