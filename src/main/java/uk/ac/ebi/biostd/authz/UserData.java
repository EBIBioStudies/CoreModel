package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@IdClass(UserDataPK.class)
@Entity
@NamedQueries({
 @NamedQuery(name="UserData.get", query="select ud from UserData ud where ud.dataKey=:key AND ud.userId = :uid"),
})
public class UserData
{
 private long userId;
 private String dataKey;
 
 private String     data;

 @Lob
 public String getData()
 {
  return data;
 }

 public void setData(String data)
 {
  this.data = data;
 }
 
 @Id 
 public long getUserId()
 {
  return userId;
 }

 public void setUserId(long userId)
 {
  this.userId = userId;
 }
 
 @Id 
 public String getDataKey()
 {
  return dataKey;
 }

 public void setDataKey(String dataKey)
 {
  this.dataKey = dataKey;
 }

}
