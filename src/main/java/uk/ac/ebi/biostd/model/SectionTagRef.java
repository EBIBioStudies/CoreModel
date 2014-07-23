package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class SectionTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="section_id")
 @ForeignKey(name="section_fk")
 public Section getSection()
 {
  return section;
 }
 private Section section;

 public void setSubmission(Section section)
 {
  this.section = section;
 }
}
