package uk.ac.ebi.biostd.authz;

import java.io.Serializable;

public class UserDataPK implements Serializable
{
 private static final long serialVersionUID = 1L;

 private long   userId;
 private String dataKey;

 public long getUserId()
 {
  return userId;
 }

 public void setUserId(long userId)
 {
  this.userId = userId;
 }

 public String getDataKey()
 {
  return dataKey;
 }

 public void setDataKey(String dataKey)
 {
  this.dataKey = dataKey;
 }
 
 @Override
 public boolean equals(Object obj)
 {
  if( obj == this )
   return true;
  
  if( ! (obj instanceof UserDataPK ) )
   return false;
  
  UserDataPK pkObj = ( UserDataPK ) obj;
  
  return pkObj.userId == userId && dataKey.equals(pkObj.dataKey) ;
 }

 @Override
 public int hashCode()
 {
  return dataKey.hashCode() ^ Long.hashCode(userId);
 }
 
}
