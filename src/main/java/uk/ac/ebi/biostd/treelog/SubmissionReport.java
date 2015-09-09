package uk.ac.ebi.biostd.treelog;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.biostd.in.SubmissionMapping;

public class SubmissionReport
{

 private LogNode                log;
 private List<SubmissionMapping> sMap = new ArrayList<SubmissionMapping>();

 public LogNode getLog()
 {
  return log;
 }

 public void setLog(LogNode log)
 {
  this.log = log;
 }
 
 public List<SubmissionMapping> getMappings()
 {
  return sMap;
 }

 public void addSubmissionMapping( SubmissionMapping sm )
 {
  if( sMap == null )
   sMap = new ArrayList<SubmissionMapping>(); 
  
  sMap.add(sm);
 }
 
 public void setSubmissionMappings( List<SubmissionMapping> mp )
 {
  sMap=mp;
 }
 
}
