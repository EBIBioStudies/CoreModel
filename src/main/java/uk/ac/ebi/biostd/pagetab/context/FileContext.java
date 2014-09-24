package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.FileAttribute;
import uk.ac.ebi.biostd.model.FileAttributeTagRef;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.trfactory.FileAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.FileTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class FileContext extends BlockContext
{
 
 private LogNode log;
 private final FileRef fileRef;
 
 public FileContext(FileRef fr, PageTabSyntaxParser2 pars, LogNode sln)
 {
  super(BlockType.FILE, pars);
  
  fileRef = fr;
 }

 @Override
 public void parseFirstLine( List<String> cells, int ln )
 {
  String nm = null;
  
  if( cells.size() > 1 )
   nm = cells.get(1).trim();
  
  if(nm != null && nm.length() > 0)
   fileRef.setName(nm);
  else
   log.log(Level.ERROR, "(R" + ln + ",C2) File name missing");

  fileRef.setAccessTags(getParser().processAccessTags(cells, ln, 3, log));
  fileRef.setTagRefs(getParser().processTags(cells, ln, 4, FileTagRefFactory.getInstance(), log));
 }
 
 public FileRef getFileRef()
 {
  return fileRef;
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  FileAttribute attr = new FileAttribute();
  
  attr.setName(nm);
  attr.setValue(val);


  attr.setTagRefs((Collection<FileAttributeTagRef>)tags);

  attr.setHost(fileRef);
  fileRef.addAttribute(attr);
  
  return attr;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return FileAttributeTagRefFactory.getInstance();
 }


}
