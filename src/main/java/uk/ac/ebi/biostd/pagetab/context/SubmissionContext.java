package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.SubmissionAttribute;
import uk.ac.ebi.biostd.model.SubmissionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SubmissionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;

public class SubmissionContext extends BlockContext
{

 private final Submission submission;
 

 public SubmissionContext(Submission sbm )
 {
  super(BlockType.SUBMISSION);
 
  submission = sbm;
 }

 public Submission getSubmission()
 {
  return submission;
 }
 
 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  SubmissionAttribute attr = new SubmissionAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<SubmissionAttributeTagRef>)tags);

  attr.setHost(submission);
  submission.addAttribute(attr);
  
  return attr;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return SubmissionAttributeTagRefFactory.getInstance();
 }
}