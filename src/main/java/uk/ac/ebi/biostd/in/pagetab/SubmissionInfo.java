package uk.ac.ebi.biostd.in.pagetab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uk.ac.ebi.biostd.in.ElementPointer;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SubmissionInfo
{
 private Submission submission;
 
 private String accNoPrefix;
 private String accNoSuffix;
 
// private Map<String,SectionRef> sectionMap = new HashMap<String, SectionRef>();
 private Collection<SectionRef> id2gen = new ArrayList<>();
 private Map<String,SectionOccurance> localIdMap =  new HashMap<String, SectionOccurance>();
 private Map<String,SectionOccurance> parentSecMap =  new HashMap<String, SectionOccurance>();
 
 private Collection<ReferenceOccurrence> refs;

 
 private LogNode logNode;
 


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

 public void addSectionOccurance( SectionOccurance so )
 {
  localIdMap.put(so.getLocalId(), so);
 }
 
 public SectionOccurance getSectionOccurance(String accNo)
 {
  return localIdMap.get(accNo);
 }
 
 public void addParentEligibleSection( SectionOccurance so )
 {
  parentSecMap.put(so.getLocalId(), so);
 }

 public SectionOccurance getParentSection(String pAcc)
 {
  return parentSecMap.get(pAcc);
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


 public void addSec2genId( SectionRef sr )
 {
  id2gen.add(sr);
 }
 
 public Collection<SectionRef> getSec2genId()
 {
  return id2gen;
 }

 public LogNode getLogNode()
 {
  return logNode;
 }

 public void setLogNode(LogNode logNode)
 {
  this.logNode = logNode;
 }

 public void addReferenceOccurance(ElementPointer ep, AbstractAttribute ref, LogNode ln )
 {
  if( refs == null )
   refs = new ArrayList<ReferenceOccurrence>();
  
  ReferenceOccurrence ro = new ReferenceOccurrence();
  
  ro.setElementPointer(ep);
  ro.setRef(ref);
  ro.setLogNode(ln);
  
  refs.add( ro );
 }
 
 public Collection<ReferenceOccurrence> getReferenceOccurrences()
 {
  return refs;
 }
 
}
