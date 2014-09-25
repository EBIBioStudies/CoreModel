package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.SectionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SectionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.SectionTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;

public class SectionContext extends VerticalBlockContext
{

 private final Section section;
 
 public SectionContext(Section sec, PageTabSyntaxParser2 prs, LogNode sln)
 {
  super(BlockType.SECTION, prs, sln);
  
  section = sec;
 }

 public Section getSection()
 {
  return section;
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  SectionAttribute attr = new SectionAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<SectionAttributeTagRef>)tags);

  attr.setHost(section);
  section.addAttribute(attr);
  
  return attr;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return SectionAttributeTagRefFactory.getInstance();
 }

 @Override
 public void parseFirstLine(List<String> cells, int ln)
 {
  String acc = null;
  
  if( cells.size() > 1 )
   acc = cells.get(1).trim();
   
  if( acc != null && acc.length() > 0 )
   section.setAcc( acc );

  if( cells.size() > 2 )
   acc = cells.get(2).trim();
   
  if( acc != null && acc.length() > 0 )
   section.setParentAcc(acc);
 
  section.setAccessTags( getParser().processAccessTags(cells, ln, 4, log) );
  section.setTagRefs( getParser().processTags(cells, ln, 5, SectionTagRefFactory.getInstance(),log) );
 }

 
}
