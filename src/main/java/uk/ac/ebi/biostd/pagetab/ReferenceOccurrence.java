package uk.ac.ebi.biostd.pagetab;

import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.treelog.LogNode;

public class ReferenceOccurrence
{
 private int row;
 private int col;
 
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

 public int getRow()
 {
  return row;
 }

 public void setRow(int row)
 {
  this.row = row;
 }

 public int getCol()
 {
  return col;
 }

 public void setCol(int col)
 {
  this.col = col;
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
