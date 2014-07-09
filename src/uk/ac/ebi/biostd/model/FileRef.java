package uk.ac.ebi.biostd.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class FileRef
{
 private Node parentNode;
 
 private String name;
 
 private List<Attribute> attriburtes; 
 
}
