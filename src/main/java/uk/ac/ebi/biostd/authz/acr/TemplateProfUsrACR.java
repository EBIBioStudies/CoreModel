package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AuthorizationTemplate;
import uk.ac.ebi.biostd.authz.HostedProfACR;
import uk.ac.ebi.biostd.authz.User;


@Entity
public class TemplateProfUsrACR extends HostedProfACR<AuthorizationTemplate, User>
{

 public TemplateProfUsrACR()
 {}

}
