package uk.ac.ebi.biostd.in;

public class CellPointer implements ElementPointer
{
 private int col;
 private int row;
 
 public CellPointer(int r, int c )
 {
  col=c;
  row=r;
 }
 
 public int getCol()
 {
  return col;
 }

 public int getRow()
 {
  return row;
 }

 @Override
 public String getPointerStr()
 {
  return "(R"+row+",C"+col+")";
 }
 
 @Override
 public String toString()
 {
  return getPointerStr();
 }
}
