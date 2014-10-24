package uk.ac.ebi.biostd.authz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

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

 @Lob
 public byte[] getPasswordDigest()
 {
  return passwordDigest;
 }
 private byte[] passwordDigest;

 public void setPasswordDigest(byte[] pwdd)
 {
  this.passwordDigest = pwdd;
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

 public boolean checkPassword(String uPass)
 {
  if( passwordDigest == null )
   return false;
  
  MessageDigest sha1 = null;
  
  try
  {
   sha1 = MessageDigest.getInstance("SHA1");
  }
  catch(NoSuchAlgorithmException e)
  {
   //It should not happen
   
   e.printStackTrace();
  }
  
  return Arrays.equals(sha1.digest(uPass.getBytes()), passwordDigest);
 }
 
 @Transient
 public void setPassword( String pass )
 {
  MessageDigest sha1 = null;
  
  try
  {
   sha1 = MessageDigest.getInstance("SHA1");
  }
  catch(NoSuchAlgorithmException e)
  {
   //It should not happen
   
   e.printStackTrace();
  }
  
  
  setPasswordDigest( sha1.digest(pass.getBytes()) );
  
 }
} 
