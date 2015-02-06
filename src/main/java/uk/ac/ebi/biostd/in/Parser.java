package uk.ac.ebi.biostd.in;

import java.util.List;

import uk.ac.ebi.biostd.in.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.treelog.LogNode;

public abstract class Parser
{
 public static final String GeneratedAccNoRx = "\\s*!(?<tmpid>[^{]+)?(?:\\{(?<pfx>[^,}]+)?(?:,(?<sfx>[^}]+))?\\})?\\s*";

 public abstract List<SubmissionInfo> parse( String txt, LogNode topLn ) throws ParserException;

 
}
