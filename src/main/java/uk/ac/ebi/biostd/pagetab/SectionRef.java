package uk.ac.ebi.biostd.pagetab;

import uk.ac.ebi.biostd.model.Section;

public class SectionRef
{
 private Section section;
 private String  accNo;
 private String  prefix;
 private String  suffix;
 private boolean local;

 public Section getSection()
 {
  return section;
 }

 public void setSection(Section section)
 {
  this.section = section;
 }

 public String getAccNo()
 {
  return accNo;
 }

 public void setAccNo(String accNo)
 {
  this.accNo = accNo;
 }

 public String getPrefix()
 {
  return prefix;
 }

 public void setPrefix(String prefix)
 {
  this.prefix = prefix;
 }

 public String getSuffix()
 {
  return suffix;
 }

 public void setSuffix(String suffix)
 {
  this.suffix = suffix;
 }

 public boolean isLocal()
 {
  return local;
 }

 public void setLocal(boolean local)
 {
  this.local = local;
 }

}
