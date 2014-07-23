package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class SubmissionTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="submission_id")
 @ForeignKey(name="submission_fk")
 public Submission getSubmission()
 {
  return submission;
 }
 private Submission submission;

 public void setSubmission(Submission submission)
 {
  this.submission = submission;
 }
}
