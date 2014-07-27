package uk.ac.ebi.biostd.jpatest;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public interface Base
{
 @Id
 long getId();
 void setId( long id);
 
 String getName();
 void setName( String nm );
 
 @Transient
 Collection<? extends Res> getRes();

}
