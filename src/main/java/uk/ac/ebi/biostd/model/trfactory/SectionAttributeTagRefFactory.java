package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.SectionAttributeTagRef;

public class SectionAttributeTagRefFactory implements TagReferenceFactory<SectionAttributeTagRef>
{
 private static SectionAttributeTagRefFactory instance  = new SectionAttributeTagRefFactory();
 
 public static SectionAttributeTagRefFactory  getInstance()
 {
  return instance;
 }
 
 @Override
 public SectionAttributeTagRef createTagRef()
 {
  return new SectionAttributeTagRef();
 }

}
