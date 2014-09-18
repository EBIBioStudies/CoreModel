package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.SubmissionTagRef;

public class SubmissionAttributeTagRefFactory implements TagReferenceFactory<SubmissionTagRef>
{
 private static SubmissionAttributeTagRefFactory instance  = new SubmissionAttributeTagRefFactory();
 
 public static SubmissionAttributeTagRefFactory getInstance()
 {
  return instance;
 }
 
 @Override
 public SubmissionTagRef createTagRef()
 {
  return new SubmissionTagRef();
 }

}
