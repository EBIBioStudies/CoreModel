package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.Tag;
import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class Link implements Node
{

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
 @OneToMany(mappedBy="host",cascade=CascadeType.ALL)
 @OrderColumn(name="ord")
 public List<LinkAttribute> getAttributes()
 {
  if( attributes == null )
   return Collections.emptyList();

  return attributes;
 }
 private List<LinkAttribute> attributes;

 public void addAttribute( LinkAttribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<LinkAttribute>();
  
  attributes.add(nd);
  nd.setHost(this);
 }
 
 public void setAttributes( List<LinkAttribute> sn )
 {
  attributes = sn;
  
  if( sn == null )
   return;
  
  for(LinkAttribute sa : sn )
   sa.setHost(this);
 }

 
 public String getUrl()
 {
  return url;
 }
 private String url;

 public void setUrl(String url)
 {
  this.url = url;
 }
 
 public boolean isLocal()
 {
  return local;
 }
 private boolean local;

 public void setLocal(boolean local)
 {
  this.local = local;
 }
 
 @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
 @JoinColumn(name="section_id")
 public Section getHostSection()
 {
  return hostSection;
 }
 private Section hostSection;
 
 public void setHostSection( Section pr )
 {
  hostSection = pr;
 }
 

 @Override
 public AbstractAttribute addAttribute(String name, String value)
 {
  LinkAttribute sa = new LinkAttribute( name, value );
  
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
 @OneToMany(mappedBy="link",cascade=CascadeType.ALL)
 public Collection<LinkTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<LinkTagRef> tagRefs;

 public void setTagRefs(Collection<LinkTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 @Override
 public LinkTagRef addTagRef( Tag t, String val )
 {
  LinkTagRef ftr = new LinkTagRef();
  
  ftr.setTag(t);
  ftr.setParameter(val);
  
  addTagRef(ftr);
  
  return ftr;
 }
 
 public void addTagRef( LinkTagRef tr )
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
 
 public int getTableIndex()
 {
  return tableIndex;
 }
 private int tableIndex = -1;
 

 public void setTableIndex(int tableIndex)
 {
  this.tableIndex = tableIndex;
 }
}
