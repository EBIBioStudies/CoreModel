package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.GenPermACR;
import uk.ac.ebi.biostd.authz.GroupACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class SystemPermGrpACR extends GenPermACR<UserGroup> implements GroupACR
{

 public SystemPermGrpACR()
 {}

}
