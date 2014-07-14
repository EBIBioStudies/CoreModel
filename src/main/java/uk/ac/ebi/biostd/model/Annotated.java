package uk.ac.ebi.biostd.model;

public interface Annotated
{
 AbstractAttribute addAttribute(String name, String value);
 AbstractAttribute addAttribute(String name, String value, String nameQual, String valQual);
}
