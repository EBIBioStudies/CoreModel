package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="submission_attr")
public class SubmissionAttribute extends AbstractAttribute
{
 public SubmissionAttribute()
 {}
 
 public SubmissionAttribute(String name, String value)
 {
  super(name,value);
 }
 
 public SubmissionAttribute(String name, String value, String nameQual, String valQual)
 {
  super(name, value, nameQual, valQual);
 }

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="submission_id")
 @ForeignKey(name="submission_fk")
 public Submission getHost()
 {
  return host;
 }
 private Submission host;
 
 public void setHost( Submission h )
 {
  host=h;
 }
}
