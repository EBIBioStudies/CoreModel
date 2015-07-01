package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.acr.GroupPermGrpACR;
import uk.ac.ebi.biostd.authz.acr.GroupPermUsrACR;
import uk.ac.ebi.biostd.authz.acr.GroupProfGrpACR;
import uk.ac.ebi.biostd.authz.acr.GroupProfUsrACR;

@Entity
@Cacheable(true)
public class UserGroup implements AuthzSubject, AuthzObject
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

 public String getName()
 {
  return name;
 }
 private String name;
 
 public void setName(String name)
 {
  this.name = name;
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
 public Collection<User> getUsers()
 {
  return users;
 }
 private Collection<User> users;

 public void setUsers(Collection<User> users)
 {
  this.users = users;
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
 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }
}
