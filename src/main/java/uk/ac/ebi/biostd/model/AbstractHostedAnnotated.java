package uk.ac.ebi.biostd.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractHostedAnnotated<T extends AbstractAttribute> extends AbstractAnnotated<T>
{

 @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
 @JoinColumn(name="sectionId")
 public Section getHostSection()
 {
  return hostSection;
 }
 private Section hostSection;
 
 public void setHostSection( Section pr )
 {
  hostSection = pr;
 }

}
