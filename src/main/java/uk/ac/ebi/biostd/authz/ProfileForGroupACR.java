package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProfileForGroupACR extends TagACR  implements ACR
{
 ProfileForGroupACR()
 {}

 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  if( ! group.isUserCompatible(user) )
   return Permit.UNDEFINED;
  
  return profile.checkPermission(act);
 }

 @Override
 @ManyToOne
 public UserGroup getSubject()
 {
  return group;
 }
 private UserGroup group; 

 @Override
 @ManyToOne
 public PermissionProfile getPermissionUnit()
 {
  return profile;
 }
 private PermissionProfile profile; 

 public void setSubject( UserGroup gb )
 {
  group=gb;
 }

 public void setPermissionUnit( PermissionProfile pb )
 {
  profile=pb;
 }
 

}
