package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.GenPermACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class GroupPermUsrACR extends GenPermACR<UserGroup, User>
{

 GroupPermUsrACR()
 {}

}