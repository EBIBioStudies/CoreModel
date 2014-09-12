package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.GenProfACR;
import uk.ac.ebi.biostd.authz.User;


@Entity
public class TagProfUsrACR extends GenProfACR<AccessTag, User>
{

 TagProfUsrACR()
 {}

}
