package uk.ac.ebi.biostd.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "attributes")
public class Attribute
{
 String name;
 
 String value;
 double numValue;

 String attrClass;
}
