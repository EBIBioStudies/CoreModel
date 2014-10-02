package uk.ac.ebi.biostd.pagetab;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ebi.biostd.model.Submission;

public class SubmissionInfo
{
 private Submission submission;
 
 private String accNoPrefix;
 private String accNoSuffix;
 
 private Map<String,SectionRef> sectionMap = new HashMap<String, SectionRef>();

 public SubmissionInfo(Submission subm)
 {
  submission = subm;
 }

  public Submission getSubmission()
 {
  return submission;
 }

 public void setSubmission(Submission submission)
 {
  this.submission = submission;
 }

 public String getAccNoPrefix()
 {
  return accNoPrefix;
 }

 public void setAccNoPrefix(String accNoPrefix)
 {
  this.accNoPrefix = accNoPrefix;
 }

 public String getAccNoSuffix()
 {
  return accNoSuffix;
 }

 public void setAccNoSuffix(String accNoSuffix)
 {
  this.accNoSuffix = accNoSuffix;
 }

 public Map<String, SectionRef> getSectionMap()
 {
  return sectionMap;
 }

 
}
