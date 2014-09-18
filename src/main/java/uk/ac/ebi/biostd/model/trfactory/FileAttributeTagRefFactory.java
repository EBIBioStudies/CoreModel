package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.FileAttributeTagRef;

public class FileAttributeTagRefFactory implements TagReferenceFactory<FileAttributeTagRef>
{
 private static FileAttributeTagRefFactory instance  = new FileAttributeTagRefFactory();
 
 public static FileAttributeTagRefFactory getInstance()
 {
  return instance;
 }
 
 @Override
 public FileAttributeTagRef createTagRef()
 {
  return new FileAttributeTagRef();
 }

}
