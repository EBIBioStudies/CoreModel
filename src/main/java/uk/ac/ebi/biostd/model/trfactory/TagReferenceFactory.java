package uk.ac.ebi.biostd.model.trfactory;

import uk.ac.ebi.biostd.authz.TagRef;

public interface TagReferenceFactory<T extends TagRef>
{
 T createTagRef();
}

