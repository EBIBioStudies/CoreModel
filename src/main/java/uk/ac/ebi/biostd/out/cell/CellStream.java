package uk.ac.ebi.biostd.out.cell;

public interface CellStream
{
 void addCell(String cont);
 void nextCell();
 void nextRow();
}
