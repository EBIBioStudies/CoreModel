package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.FileTagRef;

public class FileTagRefFactory implements TagReferenceFactory<FileTagRef>
{
 private static FileTagRefFactory instance  = new FileTagRefFactory();
 
 public static FileTagRefFactory getInstance()
 {
  return instance;
 }
 
 @Override
 public FileTagRef createTagRef()
 {
  return new FileTagRef();
 }

}
