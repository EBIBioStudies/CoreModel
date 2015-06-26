package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class GroupPermUsrACR extends HostedPermACR<UserGroup, User>
{

 public GroupPermUsrACR()
 {}

}
