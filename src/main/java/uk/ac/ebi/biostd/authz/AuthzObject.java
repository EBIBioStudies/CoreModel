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
}
