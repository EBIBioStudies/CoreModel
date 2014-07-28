package uk.ac.ebi.biostd.authz;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class GenPermACR<ObjT,SubjT extends AuthzSubject> implements ACR, PermissionUnit
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
  
  return checkPermission(act);
 }
 
 @Override
 public Permit checkPermission(SystemAction act)
 {
  if( act != action )
   return Permit.UNDEFINED;
  
  return allow?Permit.ALLOW:Permit.DENY;
 }

 @Override
 @ManyToOne
 @JoinColumn(name="subject_id")
 public SubjT getSubject()
 {
  return subject;
 }
 private SubjT subject;

 public void setSubject( SubjT gb )
 {
  subject=gb;
 }

 public SystemAction getAction()
 {
  return action;
 }
 private SystemAction action;


 public void setAction(SystemAction action)
 {
  this.action = action;
 }


 public boolean isAllow()
 {
  return allow;
 }
 private boolean allow;


 public void setAllow(boolean allow)
 {
  this.allow = allow;
 }
 
 
 @Override
 @Transient
 public PermissionUnit getPermissionUnit()
 {
  return this;
 }


}
