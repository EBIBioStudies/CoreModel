package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.SectionAttributeTagRef;

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
 public void addAttribute(String nm, String val, String nameQ, String valQ, Collection< ? extends TagRef> tags)
 {
  SectionAttribute attr = new SectionAttribute();
  
  attr.setName(nm);
  attr.setValue(val);
  attr.setNameQualifier(nameQ);
  attr.setValueQualifier(valQ);

  attr.setTagRefs((Collection<SectionAttributeTagRef>)tags);

  attr.setHost(section);
  section.addAttribute(attr);  
 }
 
}
