package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.FileAttribute;
import uk.ac.ebi.biostd.model.FileAttributeTagRef;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.trfactory.FileAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;

public class FileContext extends BlockContext
{

 private final FileRef fileRef;
 
 public FileContext(FileRef fr)
 {
  super(BlockType.SECTION);
  
  fileRef = fr;
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
