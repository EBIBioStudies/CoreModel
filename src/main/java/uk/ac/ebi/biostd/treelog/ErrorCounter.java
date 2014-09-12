package uk.ac.ebi.biostd.treelog;

public interface ErrorCounter
{
 int getErrorCounter();
 void incErrorCounter();
 void addErrorCounter(int countErrors);
 void resetErrorCounter();
}
