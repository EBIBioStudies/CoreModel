package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.User;


@Entity
public class TagPermUsrACR extends HostedPermACR<AccessTag, User>
{

 public TagPermUsrACR()
 {}

}
