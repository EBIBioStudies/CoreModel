package uk.ac.ebi.biostd.idgen.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.idgen.IdGen;


@Entity
public class IdGenPermUsrACR extends HostedPermACR<IdGen, User>
{

 public IdGenPermUsrACR()
 {}
 

}