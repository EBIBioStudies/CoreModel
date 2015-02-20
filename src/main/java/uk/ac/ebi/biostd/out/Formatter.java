package uk.ac.ebi.biostd.out;

import java.io.IOException;
import java.util.List;

import uk.ac.ebi.biostd.model.Submission;

public interface Formatter
{
 void format(List<Submission> s, Appendable out) throws IOException;
 void format(Submission s, Appendable out) throws IOException;
}
