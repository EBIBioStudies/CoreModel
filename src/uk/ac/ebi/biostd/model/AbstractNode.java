package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="nodetype",
    discriminatorType=DiscriminatorType.STRING
)public abstract class AbstractNode extends AbstractAttributed
{
 public String getAcc()
 {
  return acc;
 }

 public void setAcc(String acc)
 {
  this.acc = acc;
 }
 private String acc;
 
 @OneToMany
 @OrderColumn(name="index")
 public List<Section> getSections()
 {
  return sections;
 }
 private List<Section> sections;

 public void addSection( Section nd )
 {
  if( sections == null )
   sections = new ArrayList<Section>();
  
  sections.add(nd);
 }
 
 public void setSections( List<Section> sn )
 {
  sections = sn;
 }
 
 
 @OneToMany(mappedBy="node")
 @OrderColumn(name="index")
 public List<FileRef> getFileRefs()
 {
  return fileRefs;
 }
 private List<FileRef> fileRefs;

 public void addFileRef( FileRef nd )
 {
  if( fileRefs == null )
   fileRefs = new ArrayList<FileRef>();
  
  fileRefs.add(nd);
 }
 
 public void setFileRefs( List<FileRef> sn )
 {
  fileRefs = sn;
 }
 
 @OneToMany
 @OrderColumn(name="index")
 public List<Link> getLinks()
 {
  return links;
 }
 private List<Link> links;

 public void addLink( Link nd )
 {
  if( links == null )
   links = new ArrayList<Link>();
  
  links.add(nd);
 }
 
 public void setLinks( List<Link> sn )
 {
  links = sn;
 }

}
