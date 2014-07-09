package uk.ac.ebi.biostd.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Node
{
 private long id;
 
 private String acc;
 private String nodeClass;
 private String nodeType;

 private Node parentNode;
 private List<Node> subnodes;
 private List<Attribute> attributes;
 private List<FileRef> file;
 private List<Link> links;
 
 /**
  * Get the id of this identifiable object.  This is the database id.
  */
 @Id
 @GeneratedValue ( strategy = GenerationType.TABLE )
 public long getId()
 {
  return id;
 }

 /**
  * Set the id of this identifiable object.  This id is the oracle database id.
  * You should never explicitly set this, Hibernate will handle the creation of this ID whenever a new object is saved.
  * 
  */
 protected void setId ( long id ) 
 {
  this.id = id;
 }



}
