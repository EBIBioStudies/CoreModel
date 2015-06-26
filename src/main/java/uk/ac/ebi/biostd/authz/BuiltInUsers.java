package uk.ac.ebi.biostd.authz;


public enum BuiltInUsers
{
 Guest("@Guest","Anonymous user"),
 System("@System","Represents system owned objects");
 
 private String name;
 private String description;
 
 BuiltInUsers( String nm, String dsc )
 {
  name=nm;
  description = dsc;
 }
 
 public String getUserName()
 {
  return name;
 }
 
 public String getDescription()
 {
  return description;
 }
 
 public static boolean isBuiltIn( String gname )
 {
  for( BuiltInUsers g : values() )
  {
   if( g.getUserName().equals( gname ) )
    return true;
  }
  
  return false;
 }

}
