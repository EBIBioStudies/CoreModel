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
public class FileRef implements Node
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
 public List<FileAttribute> getAttributes()
 {
  if( attributes == null )
   return Collections.emptyList();

  return attributes;
 }
 private List<FileAttribute> attributes;

 public void addAttribute( FileAttribute nd )
 {
  if( attributes == null )
   attributes = new ArrayList<FileAttribute>();
  
  attributes.add(nd);
  nd.setHost(this);
 }
 
 public void setAttributes( List<FileAttribute> sn )
 {
  attributes = sn;
  
  for(FileAttribute sa : sn )
   sa.setHost(this);
 }
 
 public String getName()
 {
  return name;
 }
 private String name;

 public void setName(String name)
 {
  this.name = name;
 }
 
 @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
 @JoinColumn(name="sectionId")
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
  FileAttribute sa = new FileAttribute( name, value );
  
  addAttribute(sa);
  
  return sa;
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
 @OneToMany(mappedBy="fileRef",cascade=CascadeType.ALL)
 public Collection<FileTagRef> getTagRefs()
 {
  return tagRefs;
 }
 private Collection<FileTagRef> tagRefs;

 public void setTagRefs(Collection<FileTagRef> tags)
 {
  this.tagRefs = tags;
 }
 
 @Override
 public FileTagRef addTagRef( Tag t, String val )
 {
  FileTagRef ftr = new FileTagRef();
  
  ftr.setTag(t);
  ftr.setParameter(val);
  
  addTagRef(ftr);
  
  return ftr;
 }

 public void addTagRef( FileTagRef tr )
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

 @Override
 public boolean removeAttribute(AbstractAttribute at)
 {
  if( attributes == null )
   return false;
  
  return attributes.remove(at);
 }
}
