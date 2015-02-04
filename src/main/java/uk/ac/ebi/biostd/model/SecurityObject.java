package uk.ac.ebi.biostd.model;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.AccessTag;

public interface SecurityObject
{
 Collection<AccessTag> getAccessTags();
 
 void addAccessTag( AccessTag atg );
}
