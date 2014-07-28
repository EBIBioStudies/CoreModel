package uk.ac.ebi.biostd.authz;

import java.util.Collection;


public interface ACL
{

 Collection<? extends ACR> getPermissionForUserACRs();
 Collection<? extends ACR> getPermissionForGroupACRs();

 Collection<? extends ACR> getProfileForUserACRs();
 Collection<? extends ACR> getProfileForGroupACRs();
 
}
