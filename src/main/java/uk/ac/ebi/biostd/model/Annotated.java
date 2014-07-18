package uk.ac.ebi.biostd.model;

import java.util.List;

public interface Annotated
{
 AbstractAttribute addAttribute(String name, String value);
 AbstractAttribute addAttribute(String name, String value, String nameQual, String valQual);
 
 List<? extends AbstractAttribute> getAttributes();
}
