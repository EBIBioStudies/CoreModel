package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class SectionTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="section_id")
 public Section getSection()
 {
  return section;
 }
 private Section section;

 public void setSection(Section section)
 {
  this.section = section;
 }
}
