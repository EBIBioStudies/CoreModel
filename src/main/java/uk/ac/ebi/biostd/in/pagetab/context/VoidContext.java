package uk.ac.ebi.biostd.in.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;

public class VoidContext extends BlockContext
{

 public VoidContext( )
 {
  super(BlockType.NONE,null, null );
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

 @Override
 public void parseFirstLine(List<String> parts, int lineNo)
 {
 }

 @Override
 public void parseLine(List<String> parts, int lineNo)
 {
 }

}
