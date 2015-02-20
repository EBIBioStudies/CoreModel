package uk.ac.ebi.biostd.idgen;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.authz.AuthzObject;
import uk.ac.ebi.biostd.authz.SystemAction;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.idgen.acr.IdGenPermGrpACR;
import uk.ac.ebi.biostd.idgen.acr.IdGenPermUsrACR;
import uk.ac.ebi.biostd.idgen.acr.IdGenProfGrpACR;
import uk.ac.ebi.biostd.idgen.acr.IdGenProfUsrACR;

@Entity
@NamedQueries({
 @NamedQuery(name="IdGen.getByPfxSfx",query="SELECT g FROM IdGen g WHERE ( (:prefix is null AND g.prefix is null ) OR g.prefix=:prefix) AND ( (:suffix is null AND g.suffix is null ) OR g.suffix=:suffix)")
})
@Table(   indexes = {
  @Index(name = "pfxsfx_idx", columnList = "prefix,suffix", unique=true)
})
public class IdGen implements AuthzObject
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
 
 public String getPrefix()
 {
  return prefix;
 }
 private String prefix;

 public void setPrefix(String prefix)
 {
  this.prefix = prefix;
 }
 
 public String getSuffix()
 {
  return suffix;
 }
 private String suffix;


 public void setSuffix(String suffix)
 {
  this.suffix = suffix;
 }
 
// private int  counterPadding;
// private Domain domain;

 @ManyToOne
 public Counter getCounter()
 {
  return counter;
 }
 private Counter counter;

 public void setCounter(Counter counter)
 {
  this.counter = counter;
 }
 
 
 
 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<IdGenProfGrpACR> getProfileForGroupACRs()
 {
  return profileForGroupACRs;
 }

 private Collection<IdGenProfGrpACR> profileForGroupACRs;

 public void setProfileForGroupACRs(Collection<IdGenProfGrpACR> profileForGroupACRs)
 {
  this.profileForGroupACRs = profileForGroupACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<IdGenProfUsrACR> getProfileForUserACRs()
 {
  return profileForUserACRs;
 }

 private Collection<IdGenProfUsrACR> profileForUserACRs;

 public void setProfileForUserACRs(Collection<IdGenProfUsrACR> profileForUserACRs)
 {
  this.profileForUserACRs = profileForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<IdGenPermUsrACR> getPermissionForUserACRs()
 {
  return permissionForUserACRs;
 }

 private Collection<IdGenPermUsrACR> permissionForUserACRs;

 public void setPermissionForUserACRs(Collection<IdGenPermUsrACR> permissionForUserACRs)
 {
  this.permissionForUserACRs = permissionForUserACRs;
 }

 @Override
 @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
 public Collection<IdGenPermGrpACR> getPermissionForGroupACRs()
 {
  return permissionForGroupACRs;
 }
 private Collection<IdGenPermGrpACR> permissionForGroupACRs;

 public void setPermissionForGroupACRs(Collection<IdGenPermGrpACR> permissionForGroupACRs)
 {
  this.permissionForGroupACRs = permissionForGroupACRs;
 }
 
 @Override
 public Permit checkPermission(SystemAction act, User user)
 {
  return Permit.checkPermission(act, user, this);
 }




}
