package uk.ac.ebi.biostd.authz;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({
 @NamedQuery(name="User.getByLogin", query="select u from User u where u.login=:login"),
 @NamedQuery(name="User.getByEMail", query="select u from User u where u.email=:email"),
 @NamedQuery(name="User.getCount", query="select count(u) from User u")
})
@Table(
indexes = {@Index(name = "login_index",  columnList="login", unique = true),
           @Index(name = "email_index", columnList="email",     unique = true)})
public class User implements AuthzSubject, Serializable
{
 private static final long serialVersionUID = 1L;

 @Override
 public boolean isUserCompatible(User u)
 {
  return equals(u);
 }

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
  
 public boolean isActive()
 {
  return active;
 }
 private boolean active;

 public void setActive(boolean active)
 {
  this.active = active;
 }

 public String getActivationKey()
 {
  return activationKey;
 }
 private String activationKey;

 public void setActivationKey(String activationKey)
 {
  this.activationKey = activationKey;
 }

 public String getFullName()
 {
  return fullName;
 }
 private String fullName;

 public void setFullName(String description)
 {
  this.fullName = description;
 }
 
 @Lob
 public String getAuxProfileInfo()
 {
  return auxProfileInfo;
 }
 private String auxProfileInfo;

 public void setAuxProfileInfo(String auxProfileInfo)
 {
  this.auxProfileInfo = auxProfileInfo;
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
 
 @Lob
 public byte[] getPasswordDigest()
 {
  return passwordDigest;
 }
 private transient byte[] passwordDigest;

 public void setPasswordDigest(byte[] pwdd)
 {
  this.passwordDigest = pwdd;
 }

 @ManyToMany(mappedBy = "users")
 public Collection<UserGroup> getGroups()
 {
  return groups;
 }
 private transient Collection<UserGroup> groups;

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

 public boolean isSuperuser()
 {
  return superuser;
 }
 private boolean superuser;

 public void setSuperuser(boolean superuser)
 {
  this.superuser = superuser;
 }

} 
