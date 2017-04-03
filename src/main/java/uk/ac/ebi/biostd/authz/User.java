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

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 @NamedQuery(name=User.GetByLoginQuery, query="select u from User u where u.login=:login"),
 @NamedQuery(name=User.GetByEMailQuery, query="select u from User u where u.email=:email"),
 @NamedQuery(name=User.GetByIdQuery, query="select u from User u where u.id=:id"),
 @NamedQuery(name=User.GetCountQuery, query="select count(u) from User u"),
 @NamedQuery(name=User.DelByIDsQuery, query="delete from User u where u.id in :ids")
})
@Table(
indexes = {@Index(name = "login_index",  columnList="login", unique = true),
           @Index(name = "email_index", columnList="email",     unique = true)})
public class User implements AuthzSubject, Serializable
{
 public static final String GetByLoginQuery = "User.getByLogin";
 public static final String GetByEMailQuery = "User.getByEMail";
 public static final String GetByIdQuery    = "User.getById";
 public static final String GetCountQuery   = "User.getCount";
 public static final String DelByIDsQuery   = "User.delByIDs";
 
 private static final long serialVersionUID = 1L;

 public User()
 {}
 
 @Override
 public boolean isUserCompatible(User u)
 {
  return equals(u);
 }

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
 

 public long getKeyTime()
 {
  return keyTime;
 }
 private long keyTime;

 public void setKeyTime(long keyTime)
 {
  this.keyTime = keyTime;
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
 public Set<UserGroup> getGroups()
 {
  return groups;
 }
 private transient Set<UserGroup> groups;

 public void setGroups(Set<UserGroup> groups)
 {
  this.groups = groups;
 }
 
 public boolean addGroup( UserGroup ug )
 {
  if( groups == null )
   groups = new HashSet<UserGroup>();
  
  return groups.add(ug);
 }
 
 public boolean removeGroup( UserGroup ug )
 {
  if( groups == null )
   return false;
  
  return groups.remove(ug);
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

 public static User makeCopy( User u )
 {
  User du = new User();
  
  du.setActivationKey(u.getActivationKey());
  du.setActive(u.isActive());
  du.setAuxProfileInfo(u.getAuxProfileInfo());
  du.setEmail(u.getEmail());
  du.setFullName( u.getFullName() );
  du.setId( u.getId() );
  du.setKeyTime(u.getKeyTime());
  du.setLogin( u.getLogin() );
  du.setPasswordDigest(u.getPasswordDigest());
  du.setSecret(u.getSecret());
  du.setSuperuser( u.isSuperuser() );

  du.setGroups( u.getGroups() );
  
  return du;
 }
 
} 
