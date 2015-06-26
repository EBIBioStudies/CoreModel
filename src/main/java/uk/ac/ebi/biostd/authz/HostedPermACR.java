package uk.ac.ebi.biostd.authz;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class HostedPermACR<ObjT extends AuthzObject,SubjT extends AuthzSubject> extends  GenPermACR<SubjT>
{


 @ManyToOne(fetch=FetchType.LAZY)
 @JoinColumn(name="host_id")
 public ObjT getHost()
 {
  return tag;
 }
 private ObjT tag;

 public void setHost(ObjT tag)
 {
  this.tag = tag;
 }
 
}
