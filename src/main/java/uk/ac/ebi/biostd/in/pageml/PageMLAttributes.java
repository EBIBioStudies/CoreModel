package uk.ac.ebi.biostd.in.pageml;

public enum PageMLAttributes
{
 ID( "id" ),
 ACCNO( "acc" ),
 ACCNOGLOBAL( "accGlobal" ),
 ACCESS( "access" ),
 CLASS( "tags" ),
 CTIME("ctime"),
 MTIME("mtime"),
 RTIME("rtime"),
 SECKEY("seckey"),
 TYPE( "type" ),
 URL( "url" ),
 NAME( "name" ),
 RELPATH("relPath"),
 SIZE("size");
 
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
