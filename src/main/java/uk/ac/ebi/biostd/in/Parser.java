package uk.ac.ebi.biostd.in;

import uk.ac.ebi.biostd.treelog.LogNode;

public abstract class Parser
{
 public static final String GeneratedAccNoRx = "\\s*!(?<tmpid>[^{]+)?(?:\\{(?<pfx>[^,}]+)?(?:,(?<sfx>[^}]+))?\\})?\\s*";

 public abstract PMDoc parse( String txt, LogNode topLn ) throws ParserException;

 
}
