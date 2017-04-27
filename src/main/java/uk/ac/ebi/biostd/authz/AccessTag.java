/**

Copyright 2014-2017 Functional Genomics Development Team, European Bioinformatics Institute 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author Mikhail Gostev <gostev@gmail.com>

**/

package uk.ac.ebi.biostd.authz;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.acr.DelegatePermGrpACR;
import uk.ac.ebi.biostd.authz.acr.DelegatePermUsrACR;
import uk.ac.ebi.biostd.authz.acr.DelegateProfGrpACR;
import uk.ac.ebi.biostd.authz.acr.DelegateProfUsrACR;
import uk.ac.ebi.biostd.authz.acr.TagPermGrpACR;
import uk.ac.ebi.biostd.authz.acr.TagPermUsrACR;
import uk.ac.ebi.biostd.authz.acr.TagProfGrpACR;
import uk.ac.ebi.biostd.authz.acr.TagProfUsrACR;

@Entity
@NamedQueries({
 @NamedQuery(name=AccessTag.GetByNameQuery, query="SELECT t FROM AccessTag t where t.name=:name")
})
@Table(
  indexes = {
     @Index(name = "name_idx", columnList = "name", unique=true)
  })
public class AccessTag implements AuthzObject, OwnedObject
{
 public static final String GetByNameQuery = "AccessTag.getByName";

 
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
 
 @Override
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

 @Override
 public void addPermissionForUserACR(User u, SystemAction act, boolean allow)
 {
  TagPermUsrACR acr = new TagPermUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForUserACRs == null )
   permissionForUserACRs = new ArrayList<TagPermUsrACR>();
  
  permissionForUserACRs.add(acr);
 }


 @Override
 public void addPermissionForGroupACR(UserGroup ug, SystemAction act, boolean allow)
 {
  TagPermGrpACR acr = new TagPermGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForGroupACRs == null )
   permissionForGroupACRs = new ArrayList<TagPermGrpACR>();
  
  permissionForGroupACRs.add(acr);
 }


 @Override
 public void addProfileForUserACR(User u, PermissionProfile pp)
 {
  TagProfUsrACR acr = new TagProfUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForUserACRs == null )
   profileForUserACRs = new ArrayList<TagProfUsrACR>();
  
  profileForUserACRs.add(acr);
 }


 @Override
 public void addProfileForGroupACR(UserGroup ug, PermissionProfile pp)
 {
  TagProfGrpACR acr = new TagProfGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForGroupACRs == null )
   profileForGroupACRs = new ArrayList<TagProfGrpACR>();
  
  profileForGroupACRs.add(acr);
 }
 

 public void addDelegatePermissionForUserACR(User u, SystemAction act, boolean allow)
 {
  DelegatePermUsrACR acr = new DelegatePermUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( dlgPermissionForUserACRs == null )
   dlgPermissionForUserACRs = new ArrayList<DelegatePermUsrACR>();
  
  dlgPermissionForUserACRs.add(acr);
 }


 public void addDelegatePermissionForGroupACR(UserGroup ug, SystemAction act, boolean allow)
 {
  DelegatePermGrpACR acr = new DelegatePermGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( dlgPermissionForGroupACRs == null )
   dlgPermissionForGroupACRs = new ArrayList<DelegatePermGrpACR>();
  
  dlgPermissionForGroupACRs.add(acr);
 }


 public void addDelegateProfileForUserACR(User u, PermissionProfile pp)
 {
  DelegateProfUsrACR acr = new DelegateProfUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( dlgProfileForUserACRs == null )
   dlgProfileForUserACRs = new ArrayList<DelegateProfUsrACR>();
  
  dlgProfileForUserACRs.add(acr);
 }


 public void addDelegateProfileForGroupACR(UserGroup ug, PermissionProfile pp)
 {
  DelegateProfGrpACR acr = new DelegateProfGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( dlgProfileForGroupACRs == null )
   dlgProfileForGroupACRs = new ArrayList<DelegateProfGrpACR>();
  
  dlgProfileForGroupACRs.add(acr);
 }

 
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }
 
 public Permit checkDelegatePermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, new AuthzObject()
  {
   
   @Override
   public Collection< ? extends ProfileUserACR> getProfileForUserACRs()
   {
    return AccessTag.this.getDelegateProfileForUserACRs();
   }
   
   @Override
   public Collection< ? extends ProfileGroupACR> getProfileForGroupACRs()
   {
    return AccessTag.this.getDelegateProfileForGroupACRs();
   }
   
   @Override
   public Collection< ? extends PermUserACR> getPermissionForUserACRs()
   {
    return AccessTag.this.getDelegatePermissionForUserACRs();
   }
   
   @Override
   public Collection< ? extends PermGroupACR> getPermissionForGroupACRs()
   {
    return AccessTag.this.getDelegatePermissionForGroupACRs();
   }
   
   @Override
   public Permit checkPermission(SystemAction act, User user)
   {
    return Permit.DENY;
   }

   @Override
   public void addPermissionForUserACR(User u, SystemAction act, boolean allow)
   {}

   @Override
   public void addPermissionForGroupACR(UserGroup ug, SystemAction act, boolean allow)
   {}

   @Override
   public void addProfileForUserACR(User u, PermissionProfile pp)
   {}

   @Override
   public void addProfileForGroupACR(UserGroup ug, PermissionProfile pp)
   {}
  });
 }

 
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DelegateProfGrpACR> getDelegateProfileForGroupACRs()
 {
  return dlgProfileForGroupACRs;
 }

 private Collection<DelegateProfGrpACR> dlgProfileForGroupACRs;

 public void setDelegateProfileForGroupACRs(Collection<DelegateProfGrpACR> profileForGroupACRs)
 {
  this.dlgProfileForGroupACRs = profileForGroupACRs;
 }

 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DelegateProfUsrACR> getDelegateProfileForUserACRs()
 {
  return dlgProfileForUserACRs;
 }

 private Collection<DelegateProfUsrACR> dlgProfileForUserACRs;

 public void setDelegateProfileForUserACRs(Collection<DelegateProfUsrACR> profileForUserACRs)
 {
  this.dlgProfileForUserACRs = profileForUserACRs;
 }

 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DelegatePermUsrACR> getDelegatePermissionForUserACRs()
 {
  return dlgPermissionForUserACRs;
 }
 private Collection<DelegatePermUsrACR> dlgPermissionForUserACRs;

 public void setDelegatePermissionForUserACRs(Collection<DelegatePermUsrACR> permissionForUserACRs)
 {
  this.dlgPermissionForUserACRs = permissionForUserACRs;
 }

 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<DelegatePermGrpACR> getDelegatePermissionForGroupACRs()
 {
  return dlgPermissionForGroupACRs;
 }
 private Collection<DelegatePermGrpACR> dlgPermissionForGroupACRs;

 public void setDelegatePermissionForGroupACRs(Collection<DelegatePermGrpACR> permissionForGroupACRs)
 {
  this.dlgPermissionForGroupACRs = permissionForGroupACRs;
 }
}

