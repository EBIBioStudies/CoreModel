package uk.ac.ebi.biostd.in.pagetab;

import uk.ac.ebi.biostd.in.ElementPointer;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SectionOccurance
{


 private ElementPointer elementPointer;
 
 private Section section;
 
 private LogNode secLogNode;
 
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

}
