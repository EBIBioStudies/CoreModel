package uk.ac.ebi.biostd.out;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.biostd.model.Submission;

public interface Formatter
{
 void header(Map<String,List<String>> hdrs, Appendable out) throws IOException;
 void footer(Appendable out) throws IOException;
 void format(Submission s, Appendable out) throws IOException;
 void comment(String comment, Appendable out) throws IOException;
}
