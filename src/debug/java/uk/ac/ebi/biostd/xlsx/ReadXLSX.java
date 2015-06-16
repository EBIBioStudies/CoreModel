package uk.ac.ebi.biostd.xlsx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadXLSX
{

 public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException
 {
  InputStream inp = new FileInputStream("c:/tmp/test.xlsx");
  //InputStream inp = new FileInputStream("workbook.xlsx");

  Workbook wb = WorkbookFactory.create(inp);
  Sheet sheet = wb.getSheetAt(0);
  Row row = sheet.getRow(1);
  Cell cell = row.getCell(0);

  System.out.println("Cell: "+cell.getDateCellValue());
  System.out.println("Style: "+cell.getCellStyle().getDataFormat()+" "+cell.getCellStyle().getDataFormatString());
  
  inp.close();
 }

}
