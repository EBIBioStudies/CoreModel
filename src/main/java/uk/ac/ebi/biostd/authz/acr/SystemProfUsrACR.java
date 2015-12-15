package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.GenProfACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserACR;


@Entity
public class SystemProfUsrACR extends GenProfACR<User> implements UserACR
{

 public SystemProfUsrACR()
 {}

}
