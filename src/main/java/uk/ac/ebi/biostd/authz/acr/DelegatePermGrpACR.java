package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class DelegatePermGrpACR extends HostedPermACR<AccessTag, UserGroup>
{

 public DelegatePermGrpACR()
 {}
 
}
