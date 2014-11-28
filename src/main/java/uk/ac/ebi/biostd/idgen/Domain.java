package uk.ac.ebi.biostd.idgen;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.AuthzObject;
import uk.ac.ebi.biostd.authz.SystemAction;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.idgen.acr.DomainPermGrpACR;
import uk.ac.ebi.biostd.idgen.acr.DomainPermUsrACR;
import uk.ac.ebi.biostd.idgen.acr.DomainProfGrpACR;
import uk.ac.ebi.biostd.idgen.acr.DomainProfUsrACR;

@Entity
public class Domain implements AuthzObject
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
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DomainProfGrpACR> getProfileForGroupACRs()
 {
  return profileForGroupACRs;
 }

 private Collection<DomainProfGrpACR> profileForGroupACRs;

 public void setProfileForGroupACRs(Collection<DomainProfGrpACR> profileForGroupACRs)
 {
  this.profileForGroupACRs = profileForGroupACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DomainProfUsrACR> getProfileForUserACRs()
 {
  return profileForUserACRs;
 }

 private Collection<DomainProfUsrACR> profileForUserACRs;

 public void setProfileForUserACRs(Collection<DomainProfUsrACR> profileForUserACRs)
 {
  this.profileForUserACRs = profileForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DomainPermUsrACR> getPermissionForUserACRs()
 {
  return permissionForUserACRs;
 }

 private Collection<DomainPermUsrACR> permissionForUserACRs;

 public void setPermissionForUserACRs(Collection<DomainPermUsrACR> permissionForUserACRs)
 {
  this.permissionForUserACRs = permissionForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DomainPermGrpACR> getPermissionForGroupACRs()
 {
  return permissionForGroupACRs;
 }
 private Collection<DomainPermGrpACR> permissionForGroupACRs;

 public void setPermissionForGroupACRs(Collection<DomainPermGrpACR> permissionForGroupACRs)
 {
  this.permissionForGroupACRs = permissionForGroupACRs;
 }
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }
 
 private Collection<IdGen> templates;

}
