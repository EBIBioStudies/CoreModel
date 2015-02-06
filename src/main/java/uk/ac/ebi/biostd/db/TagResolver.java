package uk.ac.ebi.biostd.db;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.Tag;

public interface TagResolver
{
 Tag getTagByName( String clsfName, String tagName );
 AccessTag getAccessTagByName( String tagName );
}