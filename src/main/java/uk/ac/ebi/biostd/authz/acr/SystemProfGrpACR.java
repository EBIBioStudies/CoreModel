package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.GenProfACR;
import uk.ac.ebi.biostd.authz.GroupACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class SystemProfGrpACR extends GenProfACR<UserGroup> implements GroupACR
{

 public SystemProfGrpACR()
 {}

}
