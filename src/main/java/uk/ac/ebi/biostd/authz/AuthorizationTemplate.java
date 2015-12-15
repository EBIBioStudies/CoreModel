package uk.ac.ebi.biostd.authz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.acr.TemplatePermGrpACR;
import uk.ac.ebi.biostd.authz.acr.TemplatePermUsrACR;
import uk.ac.ebi.biostd.authz.acr.TemplateProfGrpACR;
import uk.ac.ebi.biostd.authz.acr.TemplateProfUsrACR;

@Entity
@NamedQueries({
 @NamedQuery(name="AuthorizationTemplate.getByClassName", query="select u from AuthorizationTemplate u where u.className=:className")
})
@Table(
indexes = {@Index(name = "classname_index",  columnList="className", unique = true)}
)
public class AuthorizationTemplate implements AuthzObject, Serializable
{
 private static final long serialVersionUID = 1L;

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
 

 public String getClassName()
 {
  return className;
 }
 private String className;

 public void setClassName(String className)
 {
  this.className = className;
 }
 

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TemplateProfGrpACR> getProfileForGroupACRs()
 {
  return profileForGroupACRs;
 }

 private Collection<TemplateProfGrpACR> profileForGroupACRs;

 public void setProfileForGroupACRs(Collection<TemplateProfGrpACR> profileForGroupACRs)
 {
  this.profileForGroupACRs = profileForGroupACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TemplateProfUsrACR> getProfileForUserACRs()
 {
  return profileForUserACRs;
 }

 private Collection<TemplateProfUsrACR> profileForUserACRs;

 public void setProfileForUserACRs(Collection<TemplateProfUsrACR> profileForUserACRs)
 {
  this.profileForUserACRs = profileForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TemplatePermUsrACR> getPermissionForUserACRs()
 {
  return permissionForUserACRs;
 }

 private Collection<TemplatePermUsrACR> permissionForUserACRs;

 public void setPermissionForUserACRs(Collection<TemplatePermUsrACR> permissionForUserACRs)
 {
  this.permissionForUserACRs = permissionForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<TemplatePermGrpACR> getPermissionForGroupACRs()
 {
  return permissionForGroupACRs;
 }
 private Collection<TemplatePermGrpACR> permissionForGroupACRs;

 public void setPermissionForGroupACRs(Collection<TemplatePermGrpACR> permissionForGroupACRs)
 {
  this.permissionForGroupACRs = permissionForGroupACRs;
 }
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }


 @Override
 public void addPermissionForUserACR(User u, SystemAction act, boolean allow)
 {
  TemplatePermUsrACR acr = new TemplatePermUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForUserACRs == null )
   permissionForUserACRs = new ArrayList<TemplatePermUsrACR>();
  
  permissionForUserACRs.add(acr);
 }


 @Override
 public void addPermissionForGroupACR(UserGroup ug, SystemAction act, boolean allow)
 {
  TemplatePermGrpACR acr = new TemplatePermGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setAction(act);
  acr.setAllow(allow);
  
  if( permissionForGroupACRs == null )
   permissionForGroupACRs = new ArrayList<TemplatePermGrpACR>();
  
  permissionForGroupACRs.add(acr);
 }


 @Override
 public void addProfileForUserACR(User u, PermissionProfile pp)
 {
  TemplateProfUsrACR acr = new TemplateProfUsrACR();
  
  acr.setSubject(u);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForUserACRs == null )
   profileForUserACRs = new ArrayList<TemplateProfUsrACR>();
  
  profileForUserACRs.add(acr);
 }


 @Override
 public void addProfileForGroupACR(UserGroup ug, PermissionProfile pp)
 {
  TemplateProfGrpACR acr = new TemplateProfGrpACR();
  
  acr.setSubject(ug);
  acr.setHost(this);
  acr.setProfile(pp);
  
  if( profileForGroupACRs == null )
   profileForGroupACRs = new ArrayList<TemplateProfGrpACR>();
  
  profileForGroupACRs.add(acr);
 }

}
