package uk.ac.ebi.biostd.mng;

import uk.ac.ebi.biostd.authz.User;

public interface SessionListener
{
 void sessionOpened( User u );
 void sessionClosed( User u );
}
