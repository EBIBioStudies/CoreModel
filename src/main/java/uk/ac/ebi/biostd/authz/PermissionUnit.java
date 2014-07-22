package uk.ac.ebi.biostd.authz;

import uk.ac.ebi.biostd.authz.ACR.Permit;


public interface PermissionUnit
{
 Permit checkPermission( SystemAction act );
}
