package uk.ac.ebi.biostd.pagetab.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Reference;
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
 private List<SectionContext> sections = new ArrayList<SectionContext>();

 public SectionContext(Section sec, PageTabSyntaxParser2 prs, LogNode sln, BlockContext pc)
 {
  super(BlockType.SECTION, prs, sln, pc);
  
  section = sec;
 }

 public Section getSection()
 {
  return section;
 }

 public void addSubSection( SectionContext sc )
 {
  sections.add(sc);
 }
 
 public List<SectionContext> getSubSections()
 {
  return sections;
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

 @SuppressWarnings("unchecked")
 @Override
 public Reference addReference(String nm, String val, Collection< ? extends TagRef> tags)
 {
  Reference ref =  new Reference();
  
  
  ref.setName(nm);
  ref.setValue(val);

  ref.setTagRefs((Collection<SectionAttributeTagRef>)tags);

  ref.setHost(section);
  section.addAttribute(ref);
  
  return ref;
 }
 
 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return SectionAttributeTagRefFactory.getInstance();
 }

 @Override
 public void parseFirstLine(List<String> cells, int ln)
 {
  LogNode log = getContextLogNode();

  String acc = null;
  
  if( cells.size() > 1 )
   acc = cells.get(1).trim();
   
  if( acc != null && acc.length() > 0 )
   section.setAccNo( acc );

  acc=null;

  if( cells.size() > 2 )
   acc = cells.get(2).trim();
  
  if( acc != null && acc.length() > 0 )
   section.setParentAccNo(acc);
 
  section.setAccessTags( getParser().processAccessTags(cells, ln, 4, log) );
  section.setTagRefs( getParser().processTags(cells, ln, 5, SectionTagRefFactory.getInstance(),log) );
 }
 
}
