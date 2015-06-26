package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.User;


@Entity
public class TagProfUsrACR extends HostedProfACR<AccessTag, User>
{

 public TagProfUsrACR()
 {}

}
