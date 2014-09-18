package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.LinkAttributeTagRef;

public class LinkAttributeTagRefFactory implements TagReferenceFactory<LinkAttributeTagRef>
{
 private static LinkAttributeTagRefFactory instance  = new LinkAttributeTagRefFactory();
 
 public static LinkAttributeTagRefFactory getInstance()
 {
  return instance;
 }
 
 @Override
 public LinkAttributeTagRef createTagRef()
 {
  return new LinkAttributeTagRef();
 }

}
