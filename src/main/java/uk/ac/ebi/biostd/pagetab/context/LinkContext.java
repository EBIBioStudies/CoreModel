package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.LinkAttribute;
import uk.ac.ebi.biostd.model.LinkAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.LinkAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;

public class LinkContext extends BlockContext
{

 private final Link link;
 
 public LinkContext(Link ln)
 {
  super(BlockType.SECTION);
  
  link = ln;
 }

 public Link getLink()
 {
  return link;
 }

 @SuppressWarnings("unchecked")
 @Override
 public void addAttribute(String nm, String val, String nameQ, String valQ, Collection< ? extends TagRef> tags)
 {
  LinkAttribute attr = new LinkAttribute();
  
  attr.setName(nm);
  attr.setValue(val);
  attr.setNameQualifier(nameQ);
  attr.setValueQualifier(valQ);

  attr.setTagRefs((Collection<LinkAttributeTagRef>)tags);

  attr.setHost(link);
  link.addAttribute(attr);  
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return LinkAttributeTagRefFactory.getInstance();
 }

}
