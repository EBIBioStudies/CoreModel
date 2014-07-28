package uk.ac.ebi.biostd.authz;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@MappedSuperclass
public class GenProfACR<ObjT,SubjT extends AuthzSubject> implements ACR
{
 @Id
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="host_id")
 @ForeignKey(name="host_fk")
 public ObjT getHost()
 {
  return tag;
 }
 private ObjT tag;

 public void setHost(ObjT tag)
 {
  this.tag = tag;
 }
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  if( ! subject.isUserCompatible(user) )
   return Permit.UNDEFINED;
  
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
