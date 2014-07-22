package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User implements AuthzSubject
{

 @Override
 public boolean isUserCompatible(User u)
 {
  return equals(u);
 }

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

 public String getLogin()
 {
  return login;
 }
 private String login;

 public void setLogin(String login)
 {
  this.login = login;
 }

 public String getEmail()
 {
  return email;
 }
 private String email;

 public void setEmail(String email)
 {
  this.email = email;
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

 public String getPassword()
 {
  return password;
 }
 private String password;

 public void setPassword(String password)
 {
  this.password = password;
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
 
 @Override
 public boolean equals(Object obj)
 {
  if( obj == null || ! ( obj instanceof User ) )
   return false;
  
  return id == ((User)obj).getId();
 }
 
 @Override
 public int hashCode()
 {
  return (int)(id ^ (id >>> 32));
 }
} 
