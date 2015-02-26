package uk.ac.ebi.biostd.in;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.biostd.in.pagetab.SubmissionInfo;

public class PMDoc
{
 private List<SubmissionInfo> submissions;
 
 private Map<String,List<String>> parameters;
 
 
 public void addSubmission( SubmissionInfo si )
 {
  if( submissions == null )
   submissions = new ArrayList<SubmissionInfo>();
  
  submissions.add(si);
 }
 
 public List<SubmissionInfo> getSubmissions()
 {
  return submissions;
 }
 
 public void setSubmissions(List<SubmissionInfo> submissions)
 {
  this.submissions = submissions;
 }
 
 public void addHeader( String nm, String val )
 {
  List<String> vals = null;
  
  if( parameters == null )
   parameters = new HashMap<String, List<String>>();
  else
   vals = parameters.get(nm);
  
  if( vals == null )
   parameters.put(nm, vals=new ArrayList<String>() );
  
  vals.add(val);
 }

 public Map<String, List<String>> getHeaders()
 {
  return parameters;
 }

 public void setParameters(Map<String, List<String>> parameters)
 {
  this.parameters = parameters;
 }


}
