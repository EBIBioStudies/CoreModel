package uk.ac.ebi.biostd.pagetab;

import uk.ac.ebi.biostd.treelog.LogNode;

public class ReferenceOccurrence
{
 private int row;
 private int col;
 
 private String ref;
 private LogNode logNode;

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

 public String getRef()
 {
  return ref;
 }

 public void setRef(String ref)
 {
  this.ref = ref;
 }
 
 
}
