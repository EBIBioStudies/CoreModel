package uk.ac.ebi.biostd.idgen.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.idgen.Counter;


@Entity
public class CounterPermUsrACR extends HostedPermACR<Counter, User>
{

 public CounterPermUsrACR()
 {}
 

}