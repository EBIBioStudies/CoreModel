package uk.ac.ebi.biostd.in.pagetab;

import uk.ac.ebi.biostd.in.ElementPointer;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.treelog.LogNode;

public class ReferenceOccurrence
{


 private ElementPointer elementPointer;
 
 private AbstractAttribute ref;
 private LogNode logNode;
 private Section section;

 public ReferenceOccurrence()
 {}
 
 public LogNode getLogNode()
 {
  return logNode;
 }

 public void setLogNode(LogNode logNode)
 {
  this.logNode = logNode;
 }

 public ElementPointer getElementPointer()
 {
  return elementPointer;
 }

 public void setElementPointer(ElementPointer elementPointer)
 {
  this.elementPointer = elementPointer;
 }

 public AbstractAttribute getRef()
 {
  return ref;
 }

 public void setRef(AbstractAttribute ref)
 {
  this.ref = ref;
 }

 public Section getSection()
 {
  return section;
 }

 public void setSection(Section section)
 {
  this.section = section;
 }
 
 
}
