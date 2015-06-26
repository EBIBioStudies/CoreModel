package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class GroupProfGrpACR extends HostedProfACR<UserGroup, UserGroup>
{

 public GroupProfGrpACR()
 {}

}
