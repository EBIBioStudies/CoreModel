package uk.ac.ebi.biostd.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class Link
{
 private String url;
 private boolean local;
 
 private Section parentNode;
 private List<Attribute> attriburtes; 
 
}
