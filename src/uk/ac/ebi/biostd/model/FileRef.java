package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FileRef extends AbstractAttributed
{
 
 @ManyToOne
 @JoinColumn(name="node_fk")
 public AbstractNode getParentNode()
 {
  return parentNode;
 }
 private AbstractNode parentNode;
 
 public void setParentNode( AbstractNode pr )
 {
  parentNode = pr;
 }
 
 private String name;
 
 
}
