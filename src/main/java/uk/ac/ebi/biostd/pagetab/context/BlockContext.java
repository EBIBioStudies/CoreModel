package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Reference;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;

public abstract class BlockContext
{
 public enum BlockType
 {
  NONE,
  SUBMISSION,
  SECTION,
  FILE,
  LINK,
  SECTABLE,
  LINKTABLE,
  FILETABLE
 };

 private BlockType blockType;
 private final PageTabSyntaxParser2 parser;
 private BlockContext parentContext;
 private LogNode contextLogNode;
// private Section lastSection;

 protected BlockContext( BlockType typ, PageTabSyntaxParser2 parser, LogNode ctxln, BlockContext pc )
 {
  blockType = typ;
  this.parser = parser;
  parentContext = pc;
  contextLogNode = ctxln;
 }
 
// public Section getLastSection()
// {
//  return lastSection;
// }
 
 public PageTabSyntaxParser2 getParser()
 {
  return parser;
 }

// public void setLastSection(Section lastSection)
// {
//  this.lastSection = lastSection;
// }

 public BlockType getBlockType()
 {
  return blockType;
 }

 public void setBlockType(BlockType blockType)
 {
  this.blockType = blockType;
 }
 
 public abstract AbstractAttribute addAttribute( String nm, String val, Collection<? extends TagRef> tags );
 public Reference addReference( String nm, String val, Collection<? extends TagRef> tags )
 {
  return null;
 }

 public abstract TagReferenceFactory<?> getAttributeTagRefFactory();

 public void finish()
 {
 }

 public abstract void parseFirstLine(List<String> parts, int lineNo);

 public abstract void parseLine(List<String> parts, int lineNo);

 public BlockContext getParentContext()
 {
  return parentContext;
 }

 public LogNode getContextLogNode()
 {
  return contextLogNode;
 }

 public void setParentContext(SectionContext pCtx)
 {
  parentContext = pCtx;
 }


 
}
