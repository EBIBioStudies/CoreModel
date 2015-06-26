package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class GroupProfUsrACR extends HostedProfACR<UserGroup, User>
{

 public GroupProfUsrACR()
 {}

}
