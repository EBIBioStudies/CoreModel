package uk.ac.ebi.biostd.in.pagetab;

import java.util.List;

public interface SpreadsheetReader
{

 public abstract int getLineNumber();



 public abstract List<String> readRow(List<String> accum);

}