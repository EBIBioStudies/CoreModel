package uk.ac.ebi.biostd.idgen;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.AuthzObject;
import uk.ac.ebi.biostd.authz.PermissionProfile;
import uk.ac.ebi.biostd.authz.SystemAction;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserGroup;
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
 
 public String getName()
 {
  return name;
 }
 private String name;

 public void setName(String name)
 {
  this.name = name;
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

 @Transient
 public long incrementByNum( int num )
 {
  long first = maxCount+1;
  
  maxCount+=num;
  
  return first;
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

 @Override
 public void addPermissionForUserACR(User u, SystemAction act, boolean allow)
 {
  CounterPermUsrACR acr = new CounterPermUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForUserACRs == null )
   permissionForUserACRs = new ArrayList<CounterPermUsrACR>();
  
  permissionForUserACRs.add(acr);
 }


 @Override
 public void addPermissionForGroupACR(UserGroup ug, SystemAction act, boolean allow)
 {
  CounterPermGrpACR acr = new CounterPermGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForGroupACRs == null )
   permissionForGroupACRs = new ArrayList<CounterPermGrpACR>();
  
  permissionForGroupACRs.add(acr);
 }


 @Override
 public void addProfileForUserACR(User u, PermissionProfile pp)
 {
  CounterProfUsrACR acr = new CounterProfUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForUserACRs == null )
   profileForUserACRs = new ArrayList<CounterProfUsrACR>();
  
  profileForUserACRs.add(acr);
 }


 @Override
 public void addProfileForGroupACR(UserGroup ug, PermissionProfile pp)
 {
  CounterProfGrpACR acr = new CounterProfGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForGroupACRs == null )
   profileForGroupACRs = new ArrayList<CounterProfGrpACR>();
  
  profileForGroupACRs.add(acr);
 }



}
