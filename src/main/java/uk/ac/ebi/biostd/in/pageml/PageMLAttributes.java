package uk.ac.ebi.biostd.in.pageml;

public enum PageMLAttributes
{
 ID( "id" ),
 ACCNO( "acc" ),
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
