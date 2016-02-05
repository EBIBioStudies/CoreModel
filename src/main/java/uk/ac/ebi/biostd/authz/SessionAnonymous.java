package uk.ac.ebi.biostd.authz;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionAnonymous implements Session
{

 private static User guestUser;
 
 private static Logger log;
 
 private File sessionDir;
 
 private int tmpFileCounter = 0;
 
 private EntityManager em;
 
 private EntityManagerFactory emf;

 public SessionAnonymous( File sessDir, EntityManagerFactory fct )
 {
  if( log == null )
   log = LoggerFactory.getLogger(getClass());
  
  sessionDir = sessDir;
  emf=fct;
 }
 
 @Override
 public User getUser()
 {
  return guestUser;
 }

 @Override
 public void setUser(User user)
 {
  guestUser = user;
 }

 @Override
 public long getLastAccessTime()
 {
  return System.currentTimeMillis();
 }

 @Override
 public void setLastAccessTime(long lastAccessTime)
 {
 }

 @Override
 public String getSessionKey()
 {
  return "";
 }

 @Override
 public void setSessionKey(String sessionKey)
 {
 }

 @Override
 public File makeTempFile()
 {
  if( ! sessionDir.exists() )
   if( ! sessionDir.mkdirs() )
    log.error("Can't create session directory: "+sessionDir.getAbsolutePath());
  
  return new File( sessionDir, String.valueOf(++tmpFileCounter));
 }



 @Override
 public void destroy()
 {
  if( sessionDir != null && sessionDir.exists() )
  {
   for( File f : sessionDir.listFiles() )
    if( ! f.delete() )
     log.error("Can't delete session file: "+f.getAbsolutePath());
   
   if( ! sessionDir.delete() )
    log.error("Can't delete session directory: "+sessionDir.getAbsolutePath());
  }
  
  if( em != null && em.isOpen() )
  {
   em.close();
   em = null;
  }
  
 }

 @Override
 public boolean isCheckedIn()
 {
  return true;
 }

 @Override
 public void setCheckedIn(boolean checkIn)
 {
  if( em != null && em.isOpen() )
  {
   em.close();
   em = null;
  }
 }
 
 

 @Override
 public EntityManager getEntityManager()
 {
  if(em != null && em.isOpen())
   return em;

  em = emf.createEntityManager();
  return em;
 }
 
 @Override
 public boolean isAnonymouns()
 {
  return true;
 }

}
