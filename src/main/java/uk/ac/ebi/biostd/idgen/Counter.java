package uk.ac.ebi.biostd.idgen;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.AuthzObject;
import uk.ac.ebi.biostd.authz.SystemAction;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.idgen.acr.CounterPermGrpACR;
import uk.ac.ebi.biostd.idgen.acr.CounterPermUsrACR;
import uk.ac.ebi.biostd.idgen.acr.CounterProfGrpACR;
import uk.ac.ebi.biostd.idgen.acr.CounterProfUsrACR;

@Entity
public class Counter implements AuthzObject
{
 
// private List<IdRange> ranges;
 
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
 

 public long getMaxCount()
 {
  return maxCount;
 }
 private long maxCount;


 public void setMaxCount(long maxCount)
 {
  this.maxCount = maxCount;
 }
 

 @Transient
 public long getNextNumber()
 {
  return ++maxCount;
 }

 
 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<CounterProfGrpACR> getProfileForGroupACRs()
 {
  return profileForGroupACRs;
 }

 private Collection<CounterProfGrpACR> profileForGroupACRs;

 public void setProfileForGroupACRs(Collection<CounterProfGrpACR> profileForGroupACRs)
 {
  this.profileForGroupACRs = profileForGroupACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<CounterProfUsrACR> getProfileForUserACRs()
 {
  return profileForUserACRs;
 }

 private Collection<CounterProfUsrACR> profileForUserACRs;

 public void setProfileForUserACRs(Collection<CounterProfUsrACR> profileForUserACRs)
 {
  this.profileForUserACRs = profileForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<CounterPermUsrACR> getPermissionForUserACRs()
 {
  return permissionForUserACRs;
 }

 private Collection<CounterPermUsrACR> permissionForUserACRs;

 public void setPermissionForUserACRs(Collection<CounterPermUsrACR> permissionForUserACRs)
 {
  this.permissionForUserACRs = permissionForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<CounterPermGrpACR> getPermissionForGroupACRs()
 {
  return permissionForGroupACRs;
 }
 private Collection<CounterPermGrpACR> permissionForGroupACRs;

 public void setPermissionForGroupACRs(Collection<CounterPermGrpACR> permissionForGroupACRs)
 {
  this.permissionForGroupACRs = permissionForGroupACRs;
 }
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }



}
