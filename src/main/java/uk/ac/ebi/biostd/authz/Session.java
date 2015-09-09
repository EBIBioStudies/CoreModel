package uk.ac.ebi.biostd.authz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Session
{


 private static Logger log;
 
 private String sessionKey;
 private User user;
 private long lastAccessTime;
 private volatile int checkedIn=0;
 
 private File sessionDir;
 
 private int tmpFileCounter = 0;
 
 private EntityManager defaultEM;
 private Map<Thread, EntityManager> thrEMMap = new HashMap<Thread, EntityManager>();
 
 private EntityManagerFactory emf;

 public Session( File sessDir, EntityManagerFactory fct )
 {
  if( log == null )
   log = LoggerFactory.getLogger(getClass());
  
  sessionDir = sessDir;
  lastAccessTime = System.currentTimeMillis();
  
  emf=fct;
 }
 
 public User getUser()
 {
  return user;
 }

 public void setUser(User user)
 {
  this.user = user;
 }

 public long getLastAccessTime()
 {
  return lastAccessTime;
 }

 public void setLastAccessTime(long lastAccessTime)
 {
  this.lastAccessTime = lastAccessTime;
 }

 public String getSessionKey()
 {
  return sessionKey;
 }

 public void setSessionKey(String sessionKey)
 {
  this.sessionKey = sessionKey;
 }

 public File makeTempFile()
 {
  if( ! sessionDir.exists() )
   if( ! sessionDir.mkdirs() )
    log.error("Can't create session directory: "+sessionDir.getAbsolutePath());
  
  return new File( sessionDir, String.valueOf(++tmpFileCounter));
 }



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
  
  synchronized(thrEMMap)
  {
   if( defaultEM != null )
    defaultEM.close();
   
   for( EntityManager em : thrEMMap.values() )
    em.close();
  }
  
 }

 public boolean isCheckedIn()
 {
  return checkedIn>0;
 }

 public void setCheckedIn(boolean checkIn)
 {
  lastAccessTime = System.currentTimeMillis();
  
  if( checkIn )
  {
   checkedIn++;
  }
  else
  {
   checkedIn--;

   synchronized(thrEMMap)
   {
    EntityManager em = thrEMMap.remove(Thread.currentThread());
    
    if( em != null && em.isOpen() )
    {
     if( defaultEM == null )
      defaultEM = em;
     else
      em.close();
    }
   }
  }
  
 }
 
 public EntityManager getEntityManager()
 {
  synchronized(thrEMMap)
  {
   EntityManager em = thrEMMap.get(Thread.currentThread());
   
   if( em != null && em.isOpen() )
    return em;
   
   
   if( defaultEM != null && defaultEM.isOpen() )
   {
    em=defaultEM;
    defaultEM = null; //taken or invalid
    
    thrEMMap.put(Thread.currentThread(), em);
    return em;
   }

   defaultEM = null; //may be invalid

   em = emf.createEntityManager();
   thrEMMap.put(Thread.currentThread(), em);
   return em;

  }

 }

}
