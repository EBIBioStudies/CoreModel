package uk.ac.ebi.biostd.pageml;

public enum PageMLElements
{
 ROOT(""),
 SUBMISSION( "submission" ),
 SUBSECTIONS( "subsections" ),
 SECTION( "section" ),
 FILES( "files" ),
 FILE( "file" ),
 LINKS( "links" ),
 LINK( "link" ),
 ATTRIBUTES( "attributes" ),
 ATTRIBUTE( "attribute" );
 
 private PageMLElements( String el )
 {
  elementName = el;
 }
 
 private String elementName;

 public String getElementName()
 {
  return elementName;
 }
}
