package uk.ac.ebi.biostd.in.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.in.pagetab.ParserState;
import uk.ac.ebi.biostd.in.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.FileAttribute;
import uk.ac.ebi.biostd.model.FileAttributeTagRef;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.trfactory.FileAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.treelog.LogNode;

public class FileTableContext extends TableBlockContext
{

 private final Section parent;
 
 private FileRef current;
 
 private int tableIdx=-1;
 
 public FileTableContext( Section pSec, SubmissionInfo si, ParserState prs, LogNode sln )
 {
  super(BlockType.FILETABLE, si, prs, sln);
  
  parent = pSec;
  
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  FileAttribute attr = new FileAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<FileAttributeTagRef>)tags);

  attr.setHost(current);
  current.addAttribute(attr);
  
  return attr;
 }

 
 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return FileAttributeTagRefFactory.getInstance();
 }


 @Override
 public void parseLine(List<String> parts, int lineNo)
 {
  tableIdx++;
  
  String acc = parts.get(0).trim();
  
  current = new FileRef();
  
  current.setName( acc );
  current.setTableIndex(tableIdx);
  
  super.parseLine(parts, lineNo);

  if( parent != null )
   parent.addFileRef(current);
  
 }


}
