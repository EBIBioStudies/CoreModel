package uk.ac.ebi.biostd.in.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.in.pagetab.ParserState;
import uk.ac.ebi.biostd.in.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.LinkAttribute;
import uk.ac.ebi.biostd.model.LinkAttributeTagRef;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.trfactory.LinkAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.treelog.LogNode;

public class LinkTableContext extends TableBlockContext
{
 
 private final Section parent;
 private Link current;

 private int tableIdx=-1;
 
 public LinkTableContext(Section pSec, SubmissionInfo si, ParserState prs, LogNode sln)
 {
  super( BlockType.LINKTABLE, si, prs, sln );
  
  parent = pSec;
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  LinkAttribute attr = new LinkAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<LinkAttributeTagRef>)tags);

  attr.setHost(current);
  current.addAttribute(attr);
  
  return attr;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return LinkAttributeTagRefFactory.getInstance();
 }



 @Override
 public void parseLine(List<String> parts, int lineNo)
 {
  tableIdx++;
  String acc = parts.get(0).trim();
  
  current = new Link();
  
  current.setUrl( acc );
  current.setTableIndex(tableIdx);
  
  super.parseLine(parts, lineNo);

  if( parent != null)
   parent.addLink(current);
  
 }

}
