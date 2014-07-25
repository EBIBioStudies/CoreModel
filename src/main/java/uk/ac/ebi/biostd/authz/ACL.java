package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import uk.ac.ebi.biostd.authz.ACR.Permit;

public class ACL
{
 @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
 public Collection<ProfileForGroupACR> getProfileForGroupACRs()
 {
  return profileForGroupACRs;
 }

 private Collection<ProfileForGroupACR> profileForGroupACRs;

 public void setProfileForGroupACRs(Collection<ProfileForGroupACR> profileForGroupACRs)
 {
  this.profileForGroupACRs = profileForGroupACRs;
 }

 @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
 public Collection<ProfileForUserACR> getProfileForUserACRs()
 {
  return profileForUserACRs;
 }

 private Collection<ProfileForUserACR> profileForUserACRs;

 public void setProfileForUserACRs(Collection<ProfileForUserACR> profileForUserACRs)
 {
  this.profileForUserACRs = profileForUserACRs;
 }

 @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
 public Collection<PermissionForUserACR> getPermissionForUserACRs()
 {
  return permissionForUserACRs;
 }

 private Collection<PermissionForUserACR> permissionForUserACRs;

 public void setPermissionForUserACRs(Collection<PermissionForUserACR> permissionForUserACRs)
 {
  this.permissionForUserACRs = permissionForUserACRs;
 }

 @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
 public Collection<PermissionForGroupACR> getPermissionForGroupACRs()
 {
  return permissionForGroupACRs;
 }

 private Collection<PermissionForGroupACR> permissionForGroupACRs;

 public void setPermissionForGroupACRs(Collection<PermissionForGroupACR> permissionForGroupACRs)
 {
  this.permissionForGroupACRs = permissionForGroupACRs;
 }

 public Permit checkPermission(SystemAction act, User user)
 {
  boolean allow = false;

  if(profileForGroupACRs != null)
  {
   for(ProfileForGroupACR b : profileForGroupACRs)
   {
    Permit p = b.checkPermission(act, user);
    if(p == Permit.DENY)
     return Permit.DENY;
    else if(p == Permit.ALLOW)
     allow = true;
   }
  }

  if(profileForUserACRs != null)
  {
   for(ACR b : profileForUserACRs)
   {
    Permit p = b.checkPermission(act, user);
    if(p == Permit.DENY)
     return Permit.DENY;
    else if(p == Permit.ALLOW)
     allow = true;
   }
  }

  if(permissionForUserACRs != null)
  {
   for(ACR b : permissionForUserACRs)
   {
    Permit p = b.checkPermission(act, user);
    if(p == Permit.DENY)
     return Permit.DENY;
    else if(p == Permit.ALLOW)
     allow = true;
   }
  }

  if(permissionForGroupACRs != null)
  {
   for(ACR b : permissionForGroupACRs)
   {
    Permit p = b.checkPermission(act, user);
    if(p == Permit.DENY)
     return Permit.DENY;
    else if(p == Permit.ALLOW)
     allow = true;
   }
  }

  return allow ? Permit.ALLOW : Permit.UNDEFINED;
 }
}
