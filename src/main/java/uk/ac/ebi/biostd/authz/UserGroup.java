package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class UserGroup implements AuthzSubject
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

 public String getDescription()
 {
  return description;
 }
 private String description;

 public void setDescription(String description)
 {
  this.description = description;
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
  for( User mu : users )
   if( u == mu )
    return true;
  
  for( UserGroup gb : groups )
   if( gb.isUserCompatible(u) )
    return true;
  
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

}
