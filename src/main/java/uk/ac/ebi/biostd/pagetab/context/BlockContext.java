package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;

public abstract class BlockContext
{
 public enum BlockType
 {
  NONE,
  SUBMISSION,
  SECTION,
  FILE,
  LINK
 };

 private BlockType blockType;
 private Section lastSection;

 protected BlockContext( BlockType typ )
 {
  blockType = typ;
 }
 
 public Section getLastSection()
 {
  return lastSection;
 }

 public void setLastSection(Section lastSection)
 {
  this.lastSection = lastSection;
 }

 public BlockType getBlockType()
 {
  return blockType;
 }

 public void setBlockType(BlockType blockType)
 {
  this.blockType = blockType;
 }
 
 public abstract AbstractAttribute addAttribute( String nm, String val, Collection<? extends TagRef> tags );

 public abstract TagReferenceFactory<?> getAttributeTagRefFactory();
 
}
