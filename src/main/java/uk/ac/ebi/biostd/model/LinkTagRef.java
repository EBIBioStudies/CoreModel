package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import uk.ac.ebi.biostd.authz.TagRef;

@Entity
public class LinkTagRef extends TagRef
{

 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="link_id")
 public Link getLink()
 {
  return link;
 }
 private Link link;

 public void setLink(Link link)
 {
  this.link = link;
 }

 public void setSubmission(Link l)
 {
  this.link = l;
 }
}
