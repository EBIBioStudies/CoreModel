package uk.ac.ebi.biostd.export;

import java.io.IOException;

import uk.ac.ebi.biostd.model.Submission;

public interface Formatter
{
 void format(Submission s, Appendable out) throws IOException;
}
