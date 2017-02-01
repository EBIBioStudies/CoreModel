package uk.ac.ebi.biostd.authz;


public enum BuiltInGroups
{
 
 AuthenticatedGroup( UserGroup.builtInPrefix+"Authenticated","All authenticated users"),
 EveryoneGroup     ( UserGroup.builtInPrefix+"Everyone"     ,"All users including anonymous");
 
 private String name;
 private String description;
 
 BuiltInGroups( String nm, String dsc )
 {
  name=nm;
  description = dsc;
 }
 
 public String getGroupName()
 {
  return name;
 }
 
 public String getDescription()
 {
  return description;
 }
 
 public static boolean isBuiltIn( String gname )
 {
  for( BuiltInGroups g : values() )
  {
   if( g.getGroupName().equals( gname ) )
    return true;
  }
  
  return false;
 }

}
