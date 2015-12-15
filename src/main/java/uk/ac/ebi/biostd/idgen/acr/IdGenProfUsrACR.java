package uk.ac.ebi.biostd.idgen.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.idgen.IdGen;


@Entity
public class IdGenProfUsrACR extends HostedProfACR<IdGen, User>
{

 public IdGenProfUsrACR()
 {}
 

}