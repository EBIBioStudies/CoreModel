package uk.ac.ebi.biostd.authz;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Session
{
 private static Logger log;
 
 private String sessionKey;
 private User user;
 private long lastAccessTime;
 private boolean checkedIn=false;
 
 private File sessionDir;
 
 private int tmpFileCounter = 0;

 public Session( File sessDir )
 {
  if( log == null )
   log = LoggerFactory.getLogger(getClass());
  
  sessionDir = sessDir;
  lastAccessTime = System.currentTimeMillis();
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
 }

 public boolean isCheckedIn()
 {
  return checkedIn;
 }

 public void setCheckedIn(boolean checkedIn)
 {
  lastAccessTime = System.currentTimeMillis();
  
  this.checkedIn = checkedIn;
 }
}
