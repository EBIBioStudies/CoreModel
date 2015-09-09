package uk.ac.ebi.biostd.in;

import java.util.ArrayList;
import java.util.List;

public class SubmissionMapping
{
 private AccessionMapping       submissionMapping = new AccessionMapping();
 private List<AccessionMapping> sectionsMapping;


 public AccessionMapping getSubmissionMapping()
 {
  return submissionMapping;
 }

 public void setSubmissionMapping(AccessionMapping submissionMapping)
 {
  this.submissionMapping = submissionMapping;
 }

 public List<AccessionMapping> getSectionsMapping()
 {
  return sectionsMapping;
 }

 public void setSectionsMapping(List<AccessionMapping> sectionsMapping)
 {
  this.sectionsMapping = sectionsMapping;
 }

 public void addSectionMapping(AccessionMapping secMap)
 {
  if( sectionsMapping == null )
   sectionsMapping = new ArrayList<AccessionMapping>();
  
  sectionsMapping.add(secMap);
 }
}
