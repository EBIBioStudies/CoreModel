package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;


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
