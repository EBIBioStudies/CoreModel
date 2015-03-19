package uk.ac.ebi.biostd.in.pagetab;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.odftoolkit.simple.SpreadsheetDocument;

public class SpreadsheetReaderFactory
{
 public static SpreadsheetReader getReader( byte[] data )
 {
  try
  {
   Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(data) );
   
   return new XLSpreadsheetReader(wb);
  }
  catch( Throwable t )
  {}
  
  try
  {
   SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(new ByteArrayInputStream(data));
   
   return new ODSpreadsheetReader(doc);
  }
  catch(Throwable e)
  {
  }
  
  try
  {
   return new XMLSpreadsheetReader(new ByteArrayInputStream(data));
  }
  catch( Throwable e )
  {
  }
  
  
  int offs = 0;
  Charset cs = null;
  
  if( data.length > 2 && ( ( data[0] == (byte)0xFF && data[1] == (byte)0xFE ) || ( data[0] == (byte)0xFE && data[1] == (byte)0xFF ) ) )
   cs = Charset.forName("UTF-16");
  else if( data.length > 2 && data[0] == (byte)0xEF && data[1] == (byte)0xBB && data[2] == (byte)0xBF )
  {
   cs = Charset.forName("UTF-8");
   offs = 3;
  }
  else
   cs = Charset.forName("UTF-8");
  
  return  new CSVTSVSpreadsheetReader( new String(data,offs,data.length-offs,cs), '\0' );

  
 }
}
