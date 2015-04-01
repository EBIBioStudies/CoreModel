package uk.ac.ebi.biostd.in.pageml;

public enum PageMLElements
{
 ROOT("pmdocument"),
 HEADER( "header" ),
 SUBMISSION( "submission" ),
 SUBMISSIONS( "submissions" ),
 SUBSECTIONS( "subsections" ),
 SECTION( "section" ),
 FILES( "files" ),
 FILE( "file" ),
 LINKS( "links" ),
 LINK( "link" ),
 ATTRIBUTES( "attributes" ),
 ATTRIBUTE( "attribute" ),
 TABLE( "table" ),
 NMQUALIFIER("nmqual"),
 VALQUALIFIER("valqual"),
 NAME("name"),
 VALUE("value"),
 PATH("path"),
 URL("url");
 
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
