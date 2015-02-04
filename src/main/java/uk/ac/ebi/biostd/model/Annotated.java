package uk.ac.ebi.biostd.model;

import java.util.List;

public interface Annotated
{
 AbstractAttribute addAttribute(String name, String value);
 boolean removeAttribute( AbstractAttribute at );
 
 List<? extends AbstractAttribute> getAttributes();
}
