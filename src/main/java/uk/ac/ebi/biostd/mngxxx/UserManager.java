package uk.ac.ebi.biostd.mngxxx;

import uk.ac.ebi.biostd.authz.User;

public interface UserManager
{

 User getUserByName(String uName);

 User getUserByEmail(String prm);

 void addUser(User u);

}
