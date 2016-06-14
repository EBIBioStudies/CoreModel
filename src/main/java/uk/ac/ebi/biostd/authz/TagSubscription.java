package uk.ac.ebi.biostd.authz;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
 @NamedQuery(name="TagSubscription.getUsersByTagIds", query="SELECT DISTINCT u FROM TagSubscription ts LEFT JOIN ts.user u LEFT JOIN ts.tag tg where tg.id in (:ids)")
})
public class TagSubscription
{
 private long id; 

 private Tag tag;
 private User user;

 public TagSubscription()
 {
 }

 @ManyToOne
 @JoinColumn(name="tag_id",foreignKey = @ForeignKey(name = "tag_fk"))
 public Tag getTag()
 {
  return tag;
 }

 public void setTag(Tag tag)
 {
  this.tag = tag;
 }

 @ManyToOne
 @JoinColumn(name="user_id",foreignKey = @ForeignKey(name = "user_fk"))
 public User getUser()
 {
  return user;
 }

 public void setUser(User user)
 {
  this.user = user;
 }
 
 
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public long getId()
 {
  return id;
 }

 public void setId(long id)
 {
  this.id = id;
 }
 
}
