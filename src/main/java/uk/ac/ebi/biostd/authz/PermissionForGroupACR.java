package uk.ac.ebi.biostd.authz;



public class PermissionForGroupACR extends GenACR<Tag> implements ACR
{

 PermissionForGroupACR()
 {}


 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  if( ! subject.isUserCompatible(user) )
   return Permit.UNDEFINED;
  
  return perm.checkPermission(act);
 }

 @Override
 public UserGroup getSubject()
 {
  return subject;
 }
 private UserGroup subject;

 public void setSubject( UserGroup gb )
 {
  subject=gb;
 }

 @Override
 public Permission getPermissionUnit()
 {
  return perm;
 }
 private Permission perm;
 

 public void setPermissionUnit( Permission pb )
 {
  perm=pb;
 }


 
}
