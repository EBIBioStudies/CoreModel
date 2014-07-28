package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;


@Entity
public class DelegatePermGrpACR extends GenPermACR<AccessTag, UserGroup>
{

 DelegatePermGrpACR()
 {}
 
// @Override
// @ManyToOne(targetEntity=UserGroup.class)
// public UserGroup getSubject()
// {
//  return super.getSubject();
// }

}
