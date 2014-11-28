package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.treelog.LogNode;

public class VoidContext extends BlockContext
{

 public VoidContext( LogNode ctxln, BlockContext pc)
 {
  super(BlockType.NONE,null, ctxln, pc);
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
