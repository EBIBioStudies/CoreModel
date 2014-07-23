package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ForeignKey;

import uk.ac.ebi.biostd.authz.ACR.Permit;

@Entity
public class AccessTag
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

 public String getName()
 {
  return name;
 }

 private String name;

 public void setName(String name)
 {
  this.name = name;
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

 @OneToMany(mappedBy = "parentTag", cascade = CascadeType.ALL)
 public Collection<Tag> getSubTags()
 {
  return subTags;
 }

 private Collection<Tag> subTags;

 public void setSubTags(Collection<Tag> subTags)
 {
  this.subTags = subTags;
 }

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "parent_tag_id")
 @ForeignKey(name = "parent_tag_fk")
 public Tag getParentTag()
 {
  return parentTag;
 }

 private Tag parentTag;

 public void setParentTag(Tag patentTag)
 {
  this.parentTag = patentTag;
 }


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

