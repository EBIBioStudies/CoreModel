package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AuthorizationTemplate;
import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class TemplateProfGrpACR extends HostedProfACR<AuthorizationTemplate, UserGroup>
{

 public TemplateProfGrpACR()
 {}

}
