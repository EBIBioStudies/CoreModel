package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.GenPermACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserACR;


@Entity
public class SystemPermUsrACR extends GenPermACR<User> implements UserACR
{

 public SystemPermUsrACR()
 {}

}
