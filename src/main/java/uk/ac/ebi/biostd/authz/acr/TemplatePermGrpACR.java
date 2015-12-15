package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AuthorizationTemplate;
import uk.ac.ebi.biostd.authz.HostedPermACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class TemplatePermGrpACR extends HostedPermACR<AuthorizationTemplate, UserGroup>
{

 public TemplatePermGrpACR()
 {}
 
// @Override
// @ManyToOne(targetEntity=UserGroup.class)
// public UserGroup getSubject()
// {
//  return super.getSubject();
// }

}
