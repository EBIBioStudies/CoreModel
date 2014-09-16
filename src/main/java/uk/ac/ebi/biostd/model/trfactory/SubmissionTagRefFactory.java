package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.SubmissionTagRef;

public class SubmissionTagRefFactory implements TagReferenceFactory<SubmissionTagRef>
{
 private static SubmissionTagRefFactory instance  = new SubmissionTagRefFactory();
 
 public static SubmissionTagRefFactory getInstance()
 {
  return instance;
 }
 
 @Override
 public SubmissionTagRef createTagRef()
 {
  return new SubmissionTagRef();
 }

}
