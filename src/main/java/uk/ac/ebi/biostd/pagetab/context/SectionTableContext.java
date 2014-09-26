package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.SectionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SectionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SectionTableContext extends TableBlockContext
{

 private final String secName;
 private final Section parent;
 
 private Section current;
 private int tableIdx=-1;
 
 
 public SectionTableContext(String sName, Section pSec, PageTabSyntaxParser2 prs, LogNode sln)
 {
  super(BlockType.SECTABLE,prs,sln);
  
  secName = sName;
  parent = pSec;
  
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  SectionAttribute attr = new SectionAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<SectionAttributeTagRef>)tags);

  attr.setHost(current);
  current.addAttribute(attr);
  
  return attr;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return SectionAttributeTagRefFactory.getInstance();
 }


 @Override
 public void parseLine(List<String> parts, int lineNo)
 {
  tableIdx++;
  String acc = parts.get(0).trim();
  
  current = new Section();
  
  current.setAcc( acc );
  current.setType(secName);
  current.setTableIndex(tableIdx);
  
  super.parseLine(parts, lineNo);

  parent.addSection(current);
  
 }

 
}
