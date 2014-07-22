package uk.ac.ebi.biostd.authz;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.model.Classified;


public interface PermissionManager
{

 Permit checkSystemPermission(SystemAction act);

 Permit checkPermission(SystemAction act, Classified obj);

 Permit checkPermission(SystemAction act, User usr, Classified objId);

 Permit checkSystemPermission(SystemAction act, User usr);

 Collection<Tag> getEffectiveTags(Classified objId);

 Collection<Tag> getAllowTags(SystemAction act, User usr);
 Collection<Tag> getDenyTags(SystemAction act, User usr);

}