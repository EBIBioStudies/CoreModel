package uk.ac.ebi.biostd.pagetab.parser;

public abstract class PageTabElement
{
 protected int row;
 protected int col;

 protected PageTabElement(int row, int col)
 {
  super();
  this.row = row;
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
 
 public int getCol()
 {
  return col;
 }
 
 public void setCol(int col)
 {
  this.col = col;
 }


}