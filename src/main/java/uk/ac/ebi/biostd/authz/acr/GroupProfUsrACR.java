package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.GenProfACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class GroupProfUsrACR extends GenProfACR<UserGroup, User>
{

 GroupProfUsrACR()
 {}

}
