package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;


@Entity
public class DelegatePermUsrACR extends GenPermACR<AccessTag, User>
{

 DelegatePermUsrACR()
 {}

}
