package uk.ac.ebi.biostd.in;

public class PathPointer implements ElementPointer
{
 private String path;
 
 public PathPointer( String p)
 {
  path = p;
 }

 @Override
 public String getPointerStr()
 {
  return path;
 }
 
 @Override
 public String toString()
 {
  return getPointerStr();
 }
}
