package uk.ac.ebi.biostd.mng;

import uk.ac.ebi.biostd.authz.Session;
import uk.ac.ebi.biostd.authz.User;

public interface SessionManager
{
 User getEffectiveUser();
 
 Session createSession(User user);
 Session getSession( String sKey );
 Session getSession();
 Session getSessionByUser(String id);

 Session checkin( String sessId );
 Session checkout( );

 void shutdown();

}
