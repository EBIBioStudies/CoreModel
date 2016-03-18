package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import uk.ac.ebi.biostd.authz.ACR.Permit;

@Entity
public class PermissionProfile implements PermissionUnit
{
 
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }

 public String getDescription()
 {
  return description;
 }
 private String description;

 public void setDescription(String description)
 {
  this.description = description;
 }

 @OneToMany( cascade=CascadeType.ALL )
 public Collection<Permission> getPermissions()
 {
  return permissions;
 }
 private Collection<Permission> permissions;

 public void setPermissions(Collection<Permission> permissions)
 {
  this.permissions = permissions;
 }

 @ManyToMany
 public Collection<PermissionProfile> getProfiles()
 {
  return profiles;
 }
 private Collection<PermissionProfile> profiles;

 public void setProfiles(Collection<PermissionProfile> profiles)
 {
  this.profiles = profiles;
 }

 public boolean isPartOf(PermissionProfile pb)
 {
  if( pb.getProfiles() == null )
   return false;
  
  for( PermissionProfile gb : pb.getProfiles() )
  {
   if( equals(gb) )
    return true;
   
   if( isPartOf(gb) )
    return true;
  }
  
  return false;
 }

 @Override
 public Permit checkPermission( SystemAction act )
 {
  boolean allw = false;

  if(permissions != null)
  {
   for(Permission p : permissions)
   {
    if(act == p.getAction())
    {
     if(p.isAllow())
      allw = true;
     else
      return Permit.DENY;
    }
   }
  }

  if(profiles != null)
  {
   for(PermissionProfile pp : profiles)
   {
    Permit r = pp.checkPermission(act);

    if(r == Permit.DENY)
     return Permit.DENY;
    else if(r == Permit.ALLOW)
     allw = true;
   }
  }

  return allw ? Permit.ALLOW : Permit.UNDEFINED;

 }



}
