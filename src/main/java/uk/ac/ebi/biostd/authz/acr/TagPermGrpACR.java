package uk.ac.ebi.biostd.authz.acr;

import javax.persistence.Entity;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.GenPermACR;
import uk.ac.ebi.biostd.authz.UserGroup;


@Entity
public class TagPermGrpACR extends GenPermACR<AccessTag, UserGroup>
{

 TagPermGrpACR()
 {}
 
// @Override
// @ManyToOne(targetEntity=UserGroup.class)
// public UserGroup getSubject()
// {
//  return super.getSubject();
// }

}
