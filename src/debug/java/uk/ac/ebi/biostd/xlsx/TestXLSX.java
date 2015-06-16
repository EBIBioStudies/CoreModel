package uk.ac.ebi.biostd.xlsx;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestXLSX
{

 public static void main(String[] args) throws IOException
 {
  Workbook wb = new XSSFWorkbook();
  Sheet sheet = wb.createSheet("PageTab");
  CreationHelper createHelper = wb.getCreationHelper();
  
  int rowNum=0;
 
  Row row = sheet.createRow(rowNum++);
  
  Cell cell = row.createCell(0);
  cell.setCellValue("20/20/20");
  
   row = sheet.createRow(rowNum++);
  
  CellStyle cellStyle = wb.createCellStyle();
  cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
   
  Calendar cl = Calendar.getInstance();
  
  cl.set(1941, 5, 25);
  
  cell = row.createCell(0);
  cell.setCellValue(cl);
  cell.setCellStyle(cellStyle); 

  
  FileOutputStream fileOut = new FileOutputStream("c:/tmp/test.xlsx");
  wb.write(fileOut);
  fileOut.close();

  wb.close();
 }

}
