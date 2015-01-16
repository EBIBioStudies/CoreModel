package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.LinkAttribute;
import uk.ac.ebi.biostd.model.LinkAttributeTagRef;
import uk.ac.ebi.biostd.model.LinkReference;
import uk.ac.ebi.biostd.model.trfactory.LinkAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.LinkTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.ParserState;
import uk.ac.ebi.biostd.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class LinkContext extends VerticalBlockContext
{

 
 private final Link link;
 
 
 public LinkContext(Link lnk, SubmissionInfo si, ParserState pars, LogNode sln  )
 {
  super(BlockType.LINK, si, pars, sln );
  
  link = lnk;
 }

 @Override
 public void parseFirstLine( List<String> cells, int ln )
 {
  LogNode log = getContextLogNode();

  String nm = null;
  
  if( cells.size() > 1 )
   nm = cells.get(1).trim();
  
  if(nm != null && nm.length() > 0)
   link.setUrl(nm);
  else
   log.log(Level.ERROR, "(R" + ln + ",C2) URL missing");

  link.setAccessTags(getParserState().getParser().processAccessTags(cells, ln, 3, log));
  link.setTagRefs(getParserState().getParser().processTags(cells, ln, 4, LinkTagRefFactory.getInstance(), log));
 }
 
 public Link getLink()
 {
  return link;
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  LinkAttribute attr = new LinkAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<LinkAttributeTagRef>)tags);

  attr.setHost(link);
  link.addAttribute(attr);
  
  return attr;
 }

 @SuppressWarnings("unchecked")
 @Override
 public LinkReference addReference(String nm, String val, Collection< ? extends TagRef> tags)
 {
  LinkReference ref =  new LinkReference();
  
  
  ref.setName(nm);
  ref.setValue(val);

  ref.setTagRefs((Collection<LinkAttributeTagRef>)tags);

  ref.setHost(link);
  link.addAttribute(ref);
  
  return ref;
 }
 
 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return LinkAttributeTagRefFactory.getInstance();
 }

}
