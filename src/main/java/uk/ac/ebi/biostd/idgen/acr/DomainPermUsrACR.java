package uk.ac.ebi.biostd.idgen.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.idgen.Domain;


@Entity
public class DomainPermUsrACR extends HostedPermACR<Domain, User>
{

 public DomainPermUsrACR()
 {}
 

}