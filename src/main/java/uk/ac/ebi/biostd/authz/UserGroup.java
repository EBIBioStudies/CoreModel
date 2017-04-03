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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.acr.GroupPermGrpACR;
import uk.ac.ebi.biostd.authz.acr.GroupPermUsrACR;
import uk.ac.ebi.biostd.authz.acr.GroupProfGrpACR;
import uk.ac.ebi.biostd.authz.acr.GroupProfUsrACR;

@Entity
@NamedQueries({
 @NamedQuery(name=UserGroup.GetByIdQuery, query="select g from UserGroup g where g.id=:id")
})
@Cacheable(true)
@Table( indexes = {@Index(name = "name_index",  columnList="name", unique = true)})
public class UserGroup implements AuthzSubject, AuthzObject
{
 public static final String GetByIdQuery    = "UserGroup.getById";
 
 public static final String builtInPrefix = "@";

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
 
 @Transient
 public boolean isBuiltIn()
 {
  return name!=null && name.startsWith(builtInPrefix);
 }
 
 public boolean isProject()
 {
  return project;
 }
 private boolean project;

 public void setProject(boolean project)
 {
  this.project = project;
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
 

 public String getSecret()
 {
  return secret;
 }
 private String secret;
 
 public void setSecret(String secret)
 {
  this.secret = secret;
 }
 
 @ManyToMany
 public Collection<UserGroup> getGroups()
 {
  return groups;
 }
 private Collection<UserGroup> groups;

 public void setGroups(Collection<UserGroup> groups)
 {
  this.groups = groups;
 }
 
 public boolean isPartOf(UserGroup pb)
 {
  if( pb.getGroups() == null )
   return false;
  
  for( UserGroup gb : pb.getGroups() )
  {
   if( equals(gb) )
    return true;
   
   if( isPartOf(gb) )
    return true;
  }
  
  return false;
 }

 @Override
 public boolean isUserCompatible(User u)
 {
  if( BuiltInGroups.EveryoneGroup.getGroupName().equals( getName() ) )
   return true;
  
  if( BuiltInGroups.AuthenticatedGroup.getGroupName().equals( getName() ) )
   return ! (BuiltInUsers.Guest.getUserName().equals(u.getLogin()) || BuiltInUsers.System.getUserName().equals(u.getLogin()) );
  
  if( users != null  )
  {
   for(User mu : users)
    if(u.equals(mu))
     return true;
  }
  
  if( groups != null  )
  {
   for(UserGroup gb : groups)
    if(gb.isUserCompatible(u))
     return true;
  }
  
  return false;
 }
 
// UserWritable getUser(String userId);
// UserGroup getGroup(String partId);


 @ManyToMany
 public Set<User> getUsers()
 {
  return users;
 }
 private Set<User> users;

 public void setUsers(Set<User> users)
 {
  this.users = users;
 }
 
 public boolean addUser( User usr )
 {
  if( users == null )
   users = new HashSet<User>();
  
  return users.add(usr);
 }
 
 public boolean removeUser( User usr )
 {
  if( users == null )
   return false;
  
  return users.remove(usr);
 }
 
 
 public boolean addGroup( UserGroup grp )
 {
  if( groups == null )
   groups = new HashSet<UserGroup>();
  
  return groups.add(grp);
 }
 
 public boolean removeGroup( UserGroup grp )
 {
  if( groups == null )
   return false;
  
  return groups.remove(grp);
 }
 

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<GroupProfGrpACR> getProfileForGroupACRs()
 {
  return profileForGroupACRs;
 }

 private Collection<GroupProfGrpACR> profileForGroupACRs;

 public void setProfileForGroupACRs(Collection<GroupProfGrpACR> profileForGroupACRs)
 {
  this.profileForGroupACRs = profileForGroupACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<GroupProfUsrACR> getProfileForUserACRs()
 {
  return profileForUserACRs;
 }

 private Collection<GroupProfUsrACR> profileForUserACRs;

 public void setProfileForUserACRs(Collection<GroupProfUsrACR> profileForUserACRs)
 {
  this.profileForUserACRs = profileForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<GroupPermUsrACR> getPermissionForUserACRs()
 {
  return permissionForUserACRs;
 }

 private Collection<GroupPermUsrACR> permissionForUserACRs;

 public void setPermissionForUserACRs(Collection<GroupPermUsrACR> permissionForUserACRs)
 {
  this.permissionForUserACRs = permissionForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<GroupPermGrpACR> getPermissionForGroupACRs()
 {
  return permissionForGroupACRs;
 }
 private Collection<GroupPermGrpACR> permissionForGroupACRs;

 public void setPermissionForGroupACRs(Collection<GroupPermGrpACR> permissionForGroupACRs)
 {
  this.permissionForGroupACRs = permissionForGroupACRs;
 }
 
 @Override
 public void addPermissionForUserACR(User u, SystemAction act, boolean allow)
 {
  GroupPermUsrACR acr = new GroupPermUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForUserACRs == null )
   permissionForUserACRs = new ArrayList<GroupPermUsrACR>();
  
  permissionForUserACRs.add(acr);
 }


 @Override
 public void addPermissionForGroupACR(UserGroup ug, SystemAction act, boolean allow)
 {
  GroupPermGrpACR acr = new GroupPermGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForGroupACRs == null )
   permissionForGroupACRs = new ArrayList<GroupPermGrpACR>();
  
  permissionForGroupACRs.add(acr);
 }


 @Override
 public void addProfileForUserACR(User u, PermissionProfile pp)
 {
  GroupProfUsrACR acr = new GroupProfUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForUserACRs == null )
   profileForUserACRs = new ArrayList<GroupProfUsrACR>();
  
  profileForUserACRs.add(acr);
 }


 @Override
 public void addProfileForGroupACR(UserGroup ug, PermissionProfile pp)
 {
  GroupProfGrpACR acr = new GroupProfGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForGroupACRs == null )
   profileForGroupACRs = new ArrayList<GroupProfGrpACR>();
  
  profileForGroupACRs.add(acr);
 }
 
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }
 
 @Override
 public boolean equals(Object obj)
 {
  if( obj == null || ! ( obj instanceof UserGroup ) )
   return false;
  
  return id == ((UserGroup)obj).getId();
 }
 
 @Override
 public int hashCode()
 {
  return (int)(id ^ (id >>> 32));
 }


}
