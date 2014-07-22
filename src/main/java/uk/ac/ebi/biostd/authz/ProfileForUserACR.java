package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProfileForUserACR extends TagACR  implements ACR
{
 
 @Override
 @ManyToOne
 public User getSubject()
 {
  return subject;
 }
 private User subject;

 public void setSubject( User ub )
 {
  subject=ub;
 }

 @Override
 @ManyToOne
 public PermissionProfile getPermissionUnit()
 {
  return profile;
 }
 private PermissionProfile profile;

 public void setPermissionUnit( PermissionProfile pb )
 {
  profile=pb;
 }

 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  if( ! subject.isUserCompatible(user) )
   return Permit.UNDEFINED;
  
  return profile.checkPermission(act);
 }

}
