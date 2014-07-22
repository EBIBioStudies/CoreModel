package uk.ac.ebi.biostd.model;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.Tag;

public interface Tagged
{
 Collection<Tag> getTags();
 void setTags( Collection<Tag> tgs );
 boolean addTag( Tag t );
}
