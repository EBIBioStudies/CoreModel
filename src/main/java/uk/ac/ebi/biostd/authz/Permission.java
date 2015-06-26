package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import uk.ac.ebi.biostd.authz.ACR.Permit;

@Entity
public class Permission implements PermissionUnit
{

 
 @Override
 public Permit checkPermission(SystemAction act)
 {
  if( act != action )
   return Permit.UNDEFINED;
  
  return allow?Permit.ALLOW:Permit.DENY;
 }


 @Enumerated(EnumType.STRING)
 public SystemAction getAction()
 {
  return action;
 }
 private SystemAction action;


 public void setAction(SystemAction action)
 {
  this.action = action;
 }


 public boolean isAllow()
 {
  return allow;
 }
 private boolean allow;


 public void setAllow(boolean allow)
 {
  this.allow = allow;
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
 
// SystemAction getAction();
// boolean isAllow();
// String getDescription();
}
