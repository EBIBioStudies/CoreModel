package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.SectionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SectionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;

public class SectionContext extends BlockContext
{

 private final Section section;
 
 protected SectionContext(Section sec)
 {
  super(BlockType.SECTION);
  
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
 
}
