package uk.ac.ebi.biostd.out;

import java.io.IOException;

import uk.ac.ebi.biostd.in.PMDoc;

public interface DocumentFormatter
{

 void format(PMDoc document) throws IOException;
 
}
