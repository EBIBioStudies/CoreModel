package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class TagProfGrpACR extends HostedProfACR<AccessTag, UserGroup>
{

 public TagProfGrpACR()
 {}

}
