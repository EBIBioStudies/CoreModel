package uk.ac.ebi.biostd.pagetab;

import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SectionOccurance
{
 private int col;
 private int row;
 
 private Section section;
 
 private LogNode secLogNode;
 
 public int getCol()
 {
  return col;
 }

 public void setCol(int col)
 {
  this.col = col;
 }

 public int getRow()
 {
  return row;
 }

 public void setRow(int row)
 {
  this.row = row;
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
