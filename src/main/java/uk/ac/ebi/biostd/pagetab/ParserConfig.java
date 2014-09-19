package uk.ac.ebi.biostd.pagetab;

import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class ParserConfig
{
 private  LogNode.Level missedAccessTagLL=Level.WARN;
 private  LogNode.Level missedTagLL=Level.WARN;
 
 
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

}
