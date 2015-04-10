package uk.ac.ebi.biostd.out.cell;

import java.io.IOException;

public interface CellStream
{
 void addCell(String cont) throws IOException;
 void nextCell() throws IOException;
 void nextRow() throws IOException;
 
 void start() throws IOException;
 void finish() throws IOException;
}
