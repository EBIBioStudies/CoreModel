package uk.ac.ebi.biostd.in.pagetab.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.in.pagetab.ParserState;
import uk.ac.ebi.biostd.in.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.model.SubmissionAttribute;
import uk.ac.ebi.biostd.model.SubmissionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SubmissionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.SubmissionTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SubmissionContext extends VerticalBlockContext
{

 
 private List<SectionContext> sections = new ArrayList<SectionContext>();

 public SubmissionContext(SubmissionInfo si, ParserState pars, LogNode sln /*, BlockContext pc */ )
 {
  super(BlockType.SUBMISSION, si, pars, sln);
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
  Submission submission = getSubmissionInfo().getSubmission();
  
  LogNode log = getContextLogNode();

  String acc = null;
  
  if( cells.size() > 1 )
   acc = cells.get(1).trim();
   
   if( acc != null && acc.length() > 0 )
    submission.setAccNo( acc );
//   else
//    log.log(Level.ERROR, "(R"+ln+",C2) Missing submission ID");

   submission.setAccessTags( getParserState().getParser().processAccessTags(cells, ln, 3, log) );
   submission.setTagRefs( getParserState().getParser().processTags(cells, ln, 4, SubmissionTagRefFactory.getInstance(),log) );

 }
 
 
 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  Submission submission = getSubmissionInfo().getSubmission();

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
