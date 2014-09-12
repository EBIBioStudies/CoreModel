package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.GenPermACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class GroupPermGrpACR extends GenPermACR<UserGroup, UserGroup>
{

 GroupPermGrpACR()
 {}
 
// @Override
// @ManyToOne(targetEntity=UserGroup.class)
// public UserGroup getSubject()
// {
//  return super.getSubject();
// }

}
