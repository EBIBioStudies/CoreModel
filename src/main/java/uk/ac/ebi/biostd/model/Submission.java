package uk.ac.ebi.biostd.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 @NamedQuery(name="Submission.getAllByAcc", query="SELECT s FROM Submission s where s.accNo=:accNo"),
 @NamedQuery(name="Submission.getByOwner", query="SELECT s from Submission s JOIN s.owner u where u.id=:uid AND s.version > 0 order by s.MTime desc"),
 @NamedQuery(name="Submission.getAccByPat", query="SELECT s.accNo FROM Submission s where s.accNo LIKE :pattern"),
})
@Table(
  indexes = {
     @Index(name = "acc_idx", columnList = "accNo,version"),
     @Index(name = "rtime_idx", columnList = "RTime"),
     @Index(name = "version_idx", columnList = "version"),
     @Index(name = "released_idx", columnList = "released")
  })
public class Submission implements Node, Accessible
{
 public static final String releaseDateAttribute = "ReleaseDate";
 public static final String titleAttribute = "Title";
 public static final String rootPathAttribute = "RootPath";
 public static final String attachToAttribute = "AttachTo";
 public static final String releaseDateFormat = "(?<year>\\d{2,4})-(?<month>\\d{1,2})-(?<day>\\d{1,2})(T(?<hour>\\d{1,2}):(?<min>\\d{1,2})(:(?<sec>\\d{1,2})(\\.(?<msec>\\d{1,3})Z?)?)?)?";
 
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
 
 @Lob
 public String getRootPath()
 {
  return rootPath;
 }
 private String rootPath;

 public void setRootPath(String rootPath)
 {
  this.rootPath = rootPath;
 }
 
 @Lob
 public String getRelPath()
 {
  return relPath;
 }
 private String relPath;
 
 public void setRelPath( String rp )
 {
  relPath = rp;
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
 private long rtime=-1;
 
 public void setRTime( long tm )
 {
  rtime = tm;
 }
 
 @Transient
 public boolean isRTimeSet()
 {
  return rtime >=0 ;
 }

 public boolean isReleased()
 {
  return released;
 }
 private boolean released;
 
 public void setReleased( boolean rls )
 {
  released = rls;
 }
 
 @Lob
 public String getTitle()
 {
  return title;
 }
 private String title;

 public void setTitle( String tl )
 {
  title = tl;
 }

 
 public String createTitle()
 {
  String ttl = getTitle();
  
  if( ttl != null )
   return ttl;
  
  if( getAttributes() != null )
  {
   for(SubmissionAttribute attr : getAttributes())
   {
    if(titleAttribute.equalsIgnoreCase(attr.getName()))
    {
     ttl = attr.getValue();
     break;
    }
   }
  }
  

  if( ttl == null )
   ttl = getAccNo()+" "+SimpleDateFormat.getDateTimeInstance().format( new Date(getMTime()*1000L) );

  return ttl;
 }
 
 @ManyToOne(fetch=FetchType.LAZY)
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
 @OneToMany(fetch=FetchType.LAZY, mappedBy="host", cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 public List<SubmissionAttribute> getAttributes()
 {
  if( attributes == null )
   return Collections.emptyList();
  
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
  
  if( sn == null )
   return;
  
  for(SubmissionAttribute sa : sn )
   sa.setHost(this);
 }
 
 
 @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
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
 @OneToMany(fetch=FetchType.LAZY, mappedBy="submission",cascade=CascadeType.ALL)
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
 @ManyToMany(fetch=FetchType.LAZY)
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

 public void normalizeAttributes() throws SubmissionAttributeException
 {
  if( getAttributes() == null || getAttributes().size() == 0 )
   return;
  
  Iterator<SubmissionAttribute> saitr = getAttributes().iterator();
  
  boolean rTimeFound = false;
  boolean rootPathFound = false;
  
  String rootPathAttr = null; 
  
  while(saitr.hasNext() )
  {
   SubmissionAttribute sa = saitr.next(); 
   
   if(Submission.releaseDateAttribute.equals(sa.getName()))
   {
    saitr.remove();
    
    if(rTimeFound)
     throw new SubmissionAttributeException("Multiple '" + Submission.releaseDateAttribute + "' attributes are not allowed");

    rTimeFound = true;

    String val = sa.getValue();

    if(val != null)
    {
     val = val.trim();

     if(val.length() > 0)
     {
      Matcher mtch =  Pattern.compile(Submission.releaseDateFormat).matcher(val);

      if(!mtch.matches())
       throw new SubmissionAttributeException("Invalid '" + Submission.releaseDateAttribute + "' attribute value. Expected date in format: YYYY-MM-DD[Thh:mm[:ss[.mmm]]]");
      else
      {
       Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

       cal.set(Calendar.YEAR, Integer.parseInt(mtch.group("year")));
       cal.set(Calendar.MONTH, Integer.parseInt(mtch.group("month")) - 1);
       cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mtch.group("day")));

       String str = mtch.group("hour");

       if(str != null)
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str));

       str = mtch.group("min");

       if(str != null)
        cal.set(Calendar.MINUTE, Integer.parseInt(str));

       str = mtch.group("sec");

       if(str != null)
        cal.set(Calendar.SECOND, Integer.parseInt(str));

       setRTime( cal.getTimeInMillis() / 1000 );
       
      }
     }
    }

   }
   else if( Submission.rootPathAttribute.equals(sa.getName()) )
   {
    saitr.remove();

    if(rootPathFound)
     new SubmissionAttributeException("Multiple '" + Submission.rootPathAttribute + "' attributes are not allowed");

    rootPathFound = true;
    
    rootPathAttr = sa.getValue();
    setRootPath(rootPathAttr);
   }
   else if( Submission.titleAttribute.equals(sa.getName()) )
   {
    saitr.remove();

    setTitle( sa.getValue() );
   }

  }
  
  if( getTitle() == null )
  {
   if( getRootSection() != null && getRootSection().getAttributes() != null )
   {
    for( SectionAttribute at : getRootSection().getAttributes())
    {
     if( Submission.titleAttribute.equals(at.getName()) )
     {
      setTitle(at.getValue());
      break;
     }
    }
   }
  }

 }
 
}
