package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import uk.ac.ebi.biostd.authz.ACR.Permit;

@Entity
public class AccessTag implements ACL
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
 
 @ManyToOne
 @JoinColumn(name="owner_id")
 public User getOwner()
 {
  return owner;
 }
 private User owner;

 public void setOwner(User owner)
 {
  this.owner = owner;
 }

 @OneToMany(mappedBy = "parentTag", cascade = CascadeType.ALL)
 public Collection<AccessTag> getSubTags()
 {
  return subTags;
 }

 private Collection<AccessTag> subTags;

 public void setSubTags(Collection<AccessTag> subTags)
 {
  this.subTags = subTags;
 }

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "parent_tag_id")
 public AccessTag getParentTag()
 {
  return parentTag;
 }

 private AccessTag parentTag;

 public void setParentTag(AccessTag patentTag)
 {
  this.parentTag = patentTag;
 }

 
 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TagProfGrpACR> getProfileForGroupACRs()
 {
  return profileForGroupACRs;
 }

 private Collection<TagProfGrpACR> profileForGroupACRs;

 public void setProfileForGroupACRs(Collection<TagProfGrpACR> profileForGroupACRs)
 {
  this.profileForGroupACRs = profileForGroupACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TagProfUsrACR> getProfileForUserACRs()
 {
  return profileForUserACRs;
 }

 private Collection<TagProfUsrACR> profileForUserACRs;

 public void setProfileForUserACRs(Collection<TagProfUsrACR> profileForUserACRs)
 {
  this.profileForUserACRs = profileForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TagPermUsrACR> getPermissionForUserACRs()
 {
  return permissionForUserACRs;
 }

 private Collection<TagPermUsrACR> permissionForUserACRs;

 public void setPermissionForUserACRs(Collection<TagPermUsrACR> permissionForUserACRs)
 {
  this.permissionForUserACRs = permissionForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TagPermGrpACR> getPermissionForGroupACRs()
 {
  return permissionForGroupACRs;
 }
 private Collection<TagPermGrpACR> permissionForGroupACRs;

 public void setPermissionForGroupACRs(Collection<TagPermGrpACR> permissionForGroupACRs)
 {
  this.permissionForGroupACRs = permissionForGroupACRs;
 }

 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }
}
