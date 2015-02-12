package uk.ac.ebi.biostd.in;

import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class ParserConfig
{
 private  LogNode.Level missedAccessTagLL=Level.WARN;
 private  LogNode.Level missedTagLL=Level.WARN;
 private  boolean multipleSubmissions = true;
 
 public LogNode.Level missedAccessTagLL()
 {
  return missedAccessTagLL;
 }

 public LogNode.Level missedTagLL()
 {
  return missedTagLL;
 }
 
 public void missedAccessTagLL( LogNode.Level ll )
 {
  missedAccessTagLL = ll;
 }

 public void missedTagLL( LogNode.Level ll )
 {
  missedTagLL = ll;
 }

 public boolean isMultipleSubmissions()
 {
  return multipleSubmissions;
 }

 public void setMultipleSubmissions(boolean multipleSubmissions)
 {
  this.multipleSubmissions = multipleSubmissions;
 }

}
