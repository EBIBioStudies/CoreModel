package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.model.LinkTagRef;

public class LinkTagRefFactory implements TagReferenceFactory<LinkTagRef>
{
 private static LinkTagRefFactory instance  = new LinkTagRefFactory();
 
 public static LinkTagRefFactory getInstance()
 {
  return instance;
 }
 
 @Override
 public LinkTagRef createTagRef()
 {
  return new LinkTagRef();
 }

}
