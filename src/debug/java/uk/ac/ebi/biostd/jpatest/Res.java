package uk.ac.ebi.biostd.jpatest;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="allres")
@DiscriminatorColumn(name="CTYPE")
@DiscriminatorValue("res")
public abstract class Res
{
// @Id
// abstract long getId();
// abstract void setId( long id);
 
 abstract Base getBase();
 abstract void setBase( Base b );
 
 abstract String getName();
 abstract void setName(String n);
}
