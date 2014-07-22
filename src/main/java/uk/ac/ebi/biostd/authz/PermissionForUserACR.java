package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class PermissionForUserACR extends TagACR  implements ACR
{

 PermissionForUserACR()
 {}
 
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  if( ! subject.isUserCompatible(user) )
   return Permit.UNDEFINED;
  
  return perm.checkPermission(act);
 }

 @Override
 @ManyToOne
 public User getSubject()
 {
  return subject;
 }
 private User subject;

 @Override
 @ManyToOne
 public Permission getPermissionUnit()
 {
  return perm;
 }
 private Permission perm;
 
 public void setSubject( User ub )
 {
  subject=ub;
 }

 public void setPermissionUnit( Permission pb )
 {
  perm=pb;
 }

}
