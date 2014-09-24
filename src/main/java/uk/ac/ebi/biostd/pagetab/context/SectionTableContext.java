package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.trfactory.SectionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SectionTableContext extends BlockContext
{

 
 public SectionTableContext(String sName, Section pSec, PageTabSyntaxParser2 prs, LogNode sln)
 {
  super(BlockType.SECTABLE,prs);
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
  return SectionAttributeTagRefFactory.getInstance();
 }

 @Override
 public void parseFirstLine(List<String> parts, int lineNo)
 {
 }

 @Override
 public void parseLine(List<String> parts, int lineNo)
 {
  String atName = parts.get(0);
  
 }

}
