package uk.ac.ebi.biostd.pageml;

public enum PageMLAttributes
{
 ID( "id" ),
 ACCESS( "access" ),
 CLASS( "class" ),
 TYPE( "type" ),
 URL( "url" ),
 NAME( "name" );
 
 private PageMLAttributes( String el )
 {
  attrName = el;
 }
 
 private String attrName;

 public String getAttrName()
 {
  return attrName;
 }
}
