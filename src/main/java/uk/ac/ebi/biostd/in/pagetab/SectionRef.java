package uk.ac.ebi.biostd.in.pagetab;

import uk.ac.ebi.biostd.model.Section;


public class SectionRef
{
 private Section target;
 private String  accNo;
 private String  prefix;
 private String  suffix;
 private boolean local;

 public SectionRef(Section s)
 {
  target = s;
  accNo = s.getAccNo();
 }

 public Section getSection()
 {
  return target;
 }

 public void setSection(Section section)
 {
  this.target = section;
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
