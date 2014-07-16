package uk.ac.ebi.biostd.pagetab.parser;

public interface PumpListener
{
 void dataPumped(int k);

 void endOfStream();
}
