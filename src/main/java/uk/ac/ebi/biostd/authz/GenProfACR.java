package uk.ac.ebi.biostd.authz;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@MappedSuperclass
public class GenProfACR<SubjT extends AuthzSubject> implements ACR, PermissionUnit
{
 @Id
 @GeneratedValue
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }

 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  if( ! subject.isUserCompatible(user) )
   return Permit.UNDEFINED;
  
  return profile.checkPermission(act);
 }
 
 @Override
 public Permit checkPermission(SystemAction act)
 {
  return profile.checkPermission(act);
 }

 @OneToOne
 public PermissionProfile getProfile()
 {
  return profile;
 }
 private PermissionProfile profile;

 public void setProfile(PermissionProfile profile)
 {
  this.profile = profile;
 }

 @Override
 @ManyToOne
 public SubjT getSubject()
 {
  return subject;
 }
 private SubjT subject;

 public void setSubject( SubjT gb )
 {
  subject=gb;
 }

 @Override
 @Transient
 public PermissionUnit getPermissionUnit()
 {
  return profile;
 }


}
