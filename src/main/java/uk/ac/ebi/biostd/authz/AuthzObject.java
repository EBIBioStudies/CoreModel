package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.ACR.Permit;

public interface AuthzObject
{
 Collection<? extends ACR> getPermissionForUserACRs();
 Collection<? extends ACR> getPermissionForGroupACRs();

 Collection<? extends ACR> getProfileForUserACRs();
 Collection<? extends ACR> getProfileForGroupACRs();
 
 Permit checkPermission(SystemAction act, User user);
 
 void addPermissionForUserACR(User u, SystemAction act, boolean allow);
 void addPermissionForGroupACR(UserGroup ug, SystemAction act, boolean allow);
 void addProfileForUserACR(User u, PermissionProfile pp);
 void addProfileForGroupACR(UserGroup ug, PermissionProfile pp);
}
