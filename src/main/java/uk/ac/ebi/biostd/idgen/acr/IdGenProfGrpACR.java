package uk.ac.ebi.biostd.idgen.acr;

import javax.persistence.Entity;
import uk.ac.ebi.biostd.authz.GenPermACR;
import uk.ac.ebi.biostd.authz.GenProfACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.authz.UserGroup;

import uk.ac.ebi.biostd.idgen.IdGen;


@Entity
public class IdGenProfGrpACR extends GenProfACR<IdGen, UserGroup>
{

 IdGenProfGrpACR()
 {}
 

}