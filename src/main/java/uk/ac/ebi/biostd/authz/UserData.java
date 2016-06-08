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
 @NamedQuery(name="UserData.getAll", query="select ud from UserData ud where ud.userId = :uid"),
 @NamedQuery(name="UserData.getByTopic", query="select ud from UserData ud where ud.userId = :uid AND ( (:topic is null AND ud.topic is null) OR  ud.topic = :topic)"),
})
public class UserData
{
 private long userId;
 private String dataKey;
 private String topic;
 private String contentType;
 



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
 
 public String getTopic()
 {
  return topic;
 }

 public void setTopic(String topic)
 {
  this.topic = topic;
 }
 

 public String getContentType()
 {
  return contentType;
 }

 public void setContentType(String contentType)
 {
  this.contentType = contentType;
 }

}
