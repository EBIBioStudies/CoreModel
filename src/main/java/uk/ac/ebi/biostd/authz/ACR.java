package uk.ac.ebi.biostd.authz;


public interface ACR
{
 public enum Permit
 {
  ALLOW,
  DENY,
  UNDEFINED;
  
  static public Permit checkPermission(SystemAction act, User user, AuthzObject acl)
  {
   boolean allow = false;

   if(acl.getProfileForGroupACRs() != null)
   {
    for(ACR b : acl.getProfileForGroupACRs())
    {
     Permit p = b.checkPermission(act, user);
     if(p == Permit.DENY)
      return Permit.DENY;
     else if(p == Permit.ALLOW)
      allow = true;
    }
   }

   if(acl.getProfileForUserACRs() != null)
   {
    for(ACR b : acl.getProfileForUserACRs())
    {
     Permit p = b.checkPermission(act, user);
     if(p == Permit.DENY)
      return Permit.DENY;
     else if(p == Permit.ALLOW)
      allow = true;
    }
   }

   if(acl.getProfileForUserACRs() != null)
   {
    for(ACR b : acl.getProfileForUserACRs())
    {
     Permit p = b.checkPermission(act, user);
     if(p == Permit.DENY)
      return Permit.DENY;
     else if(p == Permit.ALLOW)
      allow = true;
    }
   }

   if(acl.getPermissionForGroupACRs() != null)
   {
    for(ACR b : acl.getPermissionForGroupACRs())
    {
     Permit p = b.checkPermission(act, user);
     if(p == Permit.DENY)
      return Permit.DENY;
     else if(p == Permit.ALLOW)
      allow = true;
    }
   }

   return allow ? Permit.ALLOW : Permit.UNDEFINED;
  }
 }
 
 Permit checkPermission( SystemAction act, User user );

 AuthzSubject getSubject();
 PermissionUnit getPermissionUnit();
}
