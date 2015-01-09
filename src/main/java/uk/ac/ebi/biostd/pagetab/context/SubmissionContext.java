package uk.ac.ebi.biostd.pagetab.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.SubmissionAttribute;
import uk.ac.ebi.biostd.model.SubmissionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SubmissionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.SubmissionTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class SubmissionContext extends VerticalBlockContext
{

 private final Submission submission;
 
 private List<SectionContext> sections = new ArrayList<SectionContext>();

 public SubmissionContext(Submission sbm, PageTabSyntaxParser2 pars, LogNode sln, BlockContext pc )
 {
  super(BlockType.SUBMISSION, pars, sln, pc);
 
  submission = sbm;
 }

 public void addSection( SectionContext sc )
 {
  sections.add(sc);
 }
 
 public List<SectionContext> getSections()
 {
  return sections;
 }
 
 @Override
 public void parseFirstLine( List<String> cells, int ln )
 {
  LogNode log = getContextLogNode();

  String acc = null;
  
  if( cells.size() > 1 )
   acc = cells.get(1).trim();
   
   if( acc != null && acc.length() > 0 )
    submission.setAccNo( acc );
   else
    log.log(Level.ERROR, "(R"+ln+",C2) Missing submission ID");

   submission.setAccessTags( getParser().processAccessTags(cells, ln, 3, log) );
   submission.setTagRefs( getParser().processTags(cells, ln, 4, SubmissionTagRefFactory.getInstance(),log) );

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
