package uk.ac.ebi.biostd.model;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.TagRef;

public interface Classified
{
 Collection<? extends TagRef> getTagRefs();
 String getEntityClass();

}
