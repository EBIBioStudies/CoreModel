package uk.ac.ebi.biostd.in.pagetab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uk.ac.ebi.biostd.in.ElementPointer;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SubmissionInfo
{
 private Submission submission;
 private Submission originalSubmission;
 
 private String accNoPrefix;
 private String accNoSuffix;
 
// private Map<String,SectionRef> sectionMap = new HashMap<String, SectionRef>();
 private Collection<SectionOccurrence> globalSec = new ArrayList<>();
 private Map<String,SectionOccurrence> localIdMap =  new HashMap<String, SectionOccurrence>();
 private Map<String,SectionOccurrence> parentSecMap =  new HashMap<String, SectionOccurrence>();
 
 private Collection<FileOccurrence> files;
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

 public void addSectionOccurance( SectionOccurrence so )
 {
  localIdMap.put(so.getLocalId(), so);
 }
 
 public SectionOccurrence getSectionOccurance(String accNo)
 {
  return localIdMap.get(accNo);
 }
 
 public void addNonTableSection( SectionOccurrence so )
 {
  parentSecMap.put(so.getLocalId(), so);
 }

 public SectionOccurrence getNonTableSection(String pAcc)
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


 public void addGlobalSection( SectionOccurrence sr )
 {
  globalSec.add(sr);
 }
 
 public Collection<SectionOccurrence> getGlobalSections()
 {
  return globalSec;
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
 
 public void addFileOccurance(ElementPointer ep, FileRef ref, LogNode ln )
 {
  if( files == null )
   files = new ArrayList<FileOccurrence>();
  
  FileOccurrence ro = new FileOccurrence();
  
  ro.setElementPointer(ep);
  ro.setFileRef(ref);
  ro.setLogNode(ln);
  
  files.add( ro );
 }
 
 public Collection<ReferenceOccurrence> getReferenceOccurrences()
 {
  return refs;
 }
 
 public Collection<FileOccurrence> getFileOccurrences()
 {
  return files;
 }

 public Submission getOriginalSubmission()
 {
  return originalSubmission;
 }

 public void setOriginalSubmission(Submission originalSubmission)
 {
  this.originalSubmission = originalSubmission;
 }

}
