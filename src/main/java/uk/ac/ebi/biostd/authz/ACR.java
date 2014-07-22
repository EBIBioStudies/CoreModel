package uk.ac.ebi.biostd.authz;


public interface ACR
{
 public enum Permit
 {
  ALLOW,
  DENY,
  UNDEFINED
 }
 
 Permit checkPermission( SystemAction act, User user );

 AuthzSubject getSubject();
 PermissionUnit getPermissionUnit();
}
