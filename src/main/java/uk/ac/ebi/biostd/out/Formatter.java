package uk.ac.ebi.biostd.out;

import java.io.IOException;

import uk.ac.ebi.biostd.model.Submission;

public interface Formatter
{
 void format(Submission s, Appendable out) throws IOException;
}
