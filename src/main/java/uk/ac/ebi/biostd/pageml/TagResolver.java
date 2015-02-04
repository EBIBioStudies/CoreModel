package uk.ac.ebi.biostd.pageml;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.model.FileTagRef;
import uk.ac.ebi.biostd.model.LinkTagRef;
import uk.ac.ebi.biostd.model.SectionTagRef;
import uk.ac.ebi.biostd.model.SubmissionTagRef;

public interface TagResolver
{

 Collection<SubmissionTagRef> getSubmissionTagRefs(String value);
 Collection<SectionTagRef> getSectionTagRefs(String value);
 Collection<FileTagRef> getFileTagRefs(String value);
 Collection<LinkTagRef> getLinkTagRefs(String value);

 Collection<AccessTag> getAccessTags(String value);

}
