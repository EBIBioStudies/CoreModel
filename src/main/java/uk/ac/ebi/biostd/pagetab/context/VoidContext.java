package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;

public class VoidContext extends BlockContext
{

 public VoidContext()
 {
  super(BlockType.NONE);
 }

 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  throw new UnsupportedOperationException("addAttribute at VoidContext");
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  throw new UnsupportedOperationException("getAttributeTagRefFactory at VoidContext");
 }

}
