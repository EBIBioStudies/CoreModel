package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.GenPermACR;
import uk.ac.ebi.biostd.authz.User;


@Entity
public class DelegatePermUsrACR extends GenPermACR<AccessTag, User>
{

 DelegatePermUsrACR()
 {}

}
