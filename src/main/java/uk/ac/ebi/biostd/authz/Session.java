package uk.ac.ebi.biostd.authz;

import java.io.File;

import javax.persistence.EntityManager;

public interface Session
{

 User getUser();

 void setUser(User user);

 long getLastAccessTime();

 void setLastAccessTime(long lastAccessTime);

 String getSessionKey();

 void setSessionKey(String sessionKey);

 File makeTempFile();

 void destroy();

 boolean isCheckedIn();
 
 boolean isAnonymouns();

 void setCheckedIn(boolean checkIn);

 EntityManager getEntityManager();

}