package uk.ac.ebi.biostd.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.Tag;
import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.authz.User;


@Entity
@NamedQueries({
 @NamedQuery(name="Submission.countByAcc", query="SELECT count(s) FROM Submission s where s.accNo=:accNo AND s.version > 0"),
 @NamedQuery(name="Submission.getByAcc", query="SELECT s FROM Submission s where s.accNo=:accNo AND s.version > 0"),
 @NamedQuery(name="Submission.getByOwner", query="SELECT s from Submission s JOIN s.owner u where u.id=:uid AND s.version > 0 order by s.MTime desc")
})
@Table(
  indexes = {
     @Index(name = "acc_idx", columnList = "accNo,version")
  })
public class Submission implements Node, Accessible
{
 public static final String releaseDateAttribute = "ReleaseDate";
 public static final String rootPathAttribute = "RootPath";
 public static final String releaseDateFormat = "(?<year>\\d{2,4})-(?<month>\\d{1,2})-(?<day>\\d{1,2})(T(?<hour>\\d{1,2}):(?<min>\\d{1,2})(:(?<sec>\\d{1,2})(\\.(?<msec>\\d{1,3}))?)?)?";
 
 @Id
 @GeneratedValue
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }
 
 @Override
 public String getAccNo()
 {
  return acc;
 }
 private String acc;

 @Override
 public void setAccNo(String acc)
 {
  this.acc = acc;
 }
 
 public int getVersion()
 {
  return ver;
 }
 private int ver;
 
 public void setVersion( int v )
 {
  ver=v;
 }
 
 public long getCTime()
 {
  return ctime;
 }
 private long ctime;
 
 public void setCTime( long tm )
 {
  ctime = tm;
 }
 
 public long getMTime()
 {
  return mtime;
 }
 private long mtime;
 
 public void setMTime( long tm )
 {
  mtime = tm;
 }
 
 public long getRTime()
 {
  return rtime;
 }
 private long rtime;
 
 public void setRTime( long tm )
 {
  rtime = tm;
 }

 @Transient
 public String getDescription()
 {
  if( description != null )
   return description;
  
  for( SubmissionAttribute attr : getAttributes() )
  {
   if( "Title".equalsIgnoreCase(attr.getName()) )
   {
    description = attr.getValue();
    break;
   }
  }
  
  if( description == null )
  {
   for( SubmissionAttribute attr : getAttributes() )
   {
    if( "Description".equalsIgnoreCase(attr.getName()) )
    {
     description = attr.getValue();
     break;
    }
   }
  }
  
  if( description == null )
  {
   description = getAccNo()+" "+SimpleDateFormat.getDateTimeInstance().format( new Date(getCTime()) );
  }
  
  return description;
 }
 private String description;


 @ManyToOne
 @JoinColumn(name="owner_id")
 public User getOwner()
 {
  return owner;
 }
 private User owner;

 public void setOwner(User owner)
 {
  this.owner = owner;
 }

 @Override
 @OneToMany(mappedBy="host",cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 public List<SubmissionAttribute> getAttributes()
 {
  return attributes;
 }
 private List<SubmissionAttribute> attributes;

 public void addAttribute( SubmissionAttribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<SubmissionAttribute>();
  
  attributes.add(nd);
  nd.setHost(this);
 }
 
 public void setAttributes( List<SubmissionAttribute> sn )
 {
  attributes = sn;
  
  for(SubmissionAttribute sa : sn )
   sa.setHost(this);
 }
 
 
 @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
 @NotNull
 public Section getRootSection()
 {
  return rootSection;
 }
 private Section rootSection;
 
 public void setRootSection(Section rootSection)
 {
  this.rootSection = rootSection;
  
  rootSection.setSubmission(this);
 }


 @Override
 public AbstractAttribute addAttribute(String name, String value)
 {
  SubmissionAttribute sa = new SubmissionAttribute( name, value);
  
  addAttribute(sa);
  
  return sa;
 }
 
 @Override
 public boolean removeAttribute(AbstractAttribute at)
 {
  if( attributes == null )
   return false;
  
  return attributes.remove(at);
 }
 
 @Override
 @Transient
 public String getEntityClass()
 {
  if( getTagRefs() == null )
   return null;
  
  StringBuilder sb = new StringBuilder();
  
  for( TagRef t : getTagRefs() )
  {
   sb.append(t.getTag().getClassifier().getName()).append(":").append(t.getTag().getName());
   
   if( t.getParameter() != null && t.getParameter().length() != 0 )
    sb.append("=").append( t.getParameter() );
   
   sb.append(",");
  }
  
  if( sb.length() > 0 )
   sb.setLength( sb.length()-1 );
  
  return sb.toString();
 }
 
 
 @Override
 @OneToMany(mappedBy="submission",cascade=CascadeType.ALL)
 public Collection<SubmissionTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<SubmissionTagRef> tagRefs;

 public void setTagRefs(Collection<SubmissionTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 @Override
 public SubmissionTagRef addTagRef( Tag t, String val )
 {
  SubmissionTagRef ftr = new SubmissionTagRef();
  
  ftr.setTag(t);
  ftr.setParameter(val);
  
  addTagRef(ftr);
  
  return ftr;
 }
 
 public void addTagRef( SubmissionTagRef tr )
 {
  if( tagRefs == null )
   tagRefs = new ArrayList<>();
   
  tagRefs.add(tr);
 }

 @Override
 @ManyToMany
 public Collection<AccessTag> getAccessTags()
 {
  return accessTags;
 }
 private Collection<AccessTag> accessTags;

 public void setAccessTags(Collection<AccessTag> accessTags)
 {
  this.accessTags = accessTags;
 }
 
 @Override
 public void addAccessTag( AccessTag t )
 {
  if( accessTags == null )
   accessTags = new ArrayList<>();
   
  accessTags.add(t);
 }
}
