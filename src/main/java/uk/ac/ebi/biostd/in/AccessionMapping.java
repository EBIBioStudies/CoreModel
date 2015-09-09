package uk.ac.ebi.biostd.in;

public class AccessionMapping
{
 private String origAcc;
 private String assignedAcc;
 private int[]  position;

 public String getOrigAcc()
 {
  return origAcc;
 }

 public void setOrigAcc(String origAcc)
 {
  this.origAcc = origAcc;
 }

 public String getAssignedAcc()
 {
  return assignedAcc;
 }

 public void setAssignedAcc(String assignedAcc)
 {
  this.assignedAcc = assignedAcc;
 }

 public int[] getPosition()
 {
  return position;
 }

 public void setPosition(int[] position)
 {
  this.position = position;
 }

}
