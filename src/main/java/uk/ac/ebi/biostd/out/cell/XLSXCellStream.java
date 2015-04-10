package uk.ac.ebi.biostd.out.cell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXCellStream implements CellStream
{
 private File file;
 private Workbook wb;
 private Sheet sheet;
 private Row row;
 
 private int rowNum;
 private int cellNum;
 
 public XLSXCellStream( File f )
 {
  file = f;
 }

 @Override
 public void addCell(String cont) throws IOException
 {
  Cell cell = row.createCell(cellNum++);
  cell.setCellValue(cont);
 }

 @Override
 public void nextCell() throws IOException
 {
  cellNum++;
 }

 @Override
 public void nextRow() throws IOException
 {
  rowNum++;
  row = sheet.createRow(rowNum);
  cellNum=0;
 }

 @Override
 public void start() throws IOException
 {
  wb = new XSSFWorkbook();
  sheet = wb.createSheet("PageTab");
  rowNum=0;
  row = sheet.createRow(rowNum);
  cellNum=0;
 }

 @Override
 public void finish() throws IOException
 {
  FileOutputStream fileOut = new FileOutputStream(file);
  wb.write(fileOut);
  fileOut.close();
 }

}
