package uk.ac.ebi.biostd.idgen.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.UserGroup;
import uk.ac.ebi.biostd.idgen.IdGen;


@Entity
public class IdGenProfGrpACR extends HostedProfACR<IdGen, UserGroup>
{

 public IdGenProfGrpACR()
 {}
 

}