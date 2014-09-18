package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.SectionTagRef;

public class SectionTagRefFactory implements TagReferenceFactory<SectionTagRef>
{
 private static SectionTagRefFactory instance  = new SectionTagRefFactory();
 
 public static SectionTagRefFactory  getInstance()
 {
  return instance;
 }
 
 @Override
 public SectionTagRef createTagRef()
 {
  return new SectionTagRef();
 }

}
