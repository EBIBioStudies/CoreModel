package uk.ac.ebi.biostd.in.pagetab;

import uk.ac.ebi.biostd.in.ElementPointer;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SectionOccurrence
{
 private ElementPointer elementPointer;
 
 private Section section;
 
 private LogNode secLogNode;
 
 private String  prefix;
 private String  suffix;
 private String  originalAccNo;
 
 public ElementPointer getElementPointer()
 {
  return elementPointer;
 }

 public void setElementPointer(ElementPointer elementPointer)
 {
  this.elementPointer = elementPointer;
 }

 public Section getSection()
 {
  return section;
 }

 public void setSection(Section section)
 {
  this.section = section;
 }

 public LogNode getSecLogNode()
 {
  return secLogNode;
 }

 public void setSecLogNode(LogNode secLogNode)
 {
  this.secLogNode = secLogNode;
 }

 public String getLocalId()
 {
  return section.getAccNo();
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

 public String getOriginalAccNo()
 {
  return originalAccNo;
 }

 public void setOriginalAccNo(String originalAccNo)
 {
  this.originalAccNo = originalAccNo;
 }
}
