package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.trfactory.LinkAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;

public class LinkTableContext extends BlockContext
{

 public LinkTableContext(Section lastSection, PageTabSyntaxParser2 prs, LogNode sln)
 {
  super(BlockType.LINKTABLE,prs);
 }

 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  // TODO Auto-generated method stub
  return null;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return LinkAttributeTagRefFactory.getInstance();
 }

 @Override
 public void parseFirstLine(List<String> parts, int lineNo)
 {
  // TODO Auto-generated method stub
  
 }

}
