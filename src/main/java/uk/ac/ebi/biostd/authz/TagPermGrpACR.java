package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;


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
