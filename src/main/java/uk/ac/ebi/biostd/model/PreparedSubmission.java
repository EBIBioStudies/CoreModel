package uk.ac.ebi.biostd.model;

import java.util.Collection;

public class PreparedSubmission
{

 private Submission submission;
 private Collection<SectionRef> globalSections;
 private String acc;
 private String accPrefix;
 private String accSuffix;
 
 public String getAcc()
 {
  return acc;
 }

 public void setAcc(String acc)
 {
  this.acc = acc;
 }

 public String getAccPrefix()
 {
  return accPrefix;
 }

 public void setAccPrefix(String accPrefix)
 {
  this.accPrefix = accPrefix;
 }

 public String getAccSuffix()
 {
  return accSuffix;
 }

 public void setAccSuffix(String accSuffix)
 {
  this.accSuffix = accSuffix;
 }


 public Submission getSubmission()
 {
  return submission;
 }
 
 public void setSubmission(Submission submission)
 {
  this.submission = submission;
 }
 
 public Collection<SectionRef> getGlobalSections()
 {
  return globalSections;
 }
 
 public void setGlobalSections(Collection<SectionRef> globalSections)
 {
  this.globalSections = globalSections;
 }
}
