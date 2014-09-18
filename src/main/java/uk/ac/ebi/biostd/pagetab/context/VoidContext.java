package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;

public class VoidContext extends BlockContext
{

 public VoidContext()
 {
  super(BlockType.NONE);
 }

 @Override
 public void addAttribute(String nm, String val, String nameQ, String valQ, Collection< ? extends TagRef> tags)
 {
  throw new UnsupportedOperationException("addAttribute at VoidContext");
 }

}
