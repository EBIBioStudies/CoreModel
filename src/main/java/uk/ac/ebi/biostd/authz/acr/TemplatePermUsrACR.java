package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AuthorizationTemplate;
import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.User;


@Entity
public class TemplatePermUsrACR extends HostedPermACR<AuthorizationTemplate, User>
{

 public TemplatePermUsrACR()
 {}

}
