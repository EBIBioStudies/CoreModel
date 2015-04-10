package uk.ac.ebi.biostd.out.cell;

import java.io.IOException;

public class XSVCellStream implements CellStream
{

 private Appendable stream;
 
 private boolean newLine;
 private char sep;
 
 public static CellStream getCSVCellStream( Appendable s )
 {
  return new XSVCellStream(s,',');
 }
 
 public static CellStream getTSVCellStream( Appendable s )
 {
  return new XSVCellStream(s,'\t');
 }

 
 public XSVCellStream( Appendable s, char sep )
 {
  stream = s;
  
  newLine = true;
  
  this.sep = sep;
 }
 
 @Override
 public void addCell(String cont) throws IOException
 {
  if(newLine)
   newLine = false;
  else
   stream.append(sep);

  int ptr = 0;
  int pos = cont.indexOf('"');
  
  if( pos == -1 )
   stream.append(cont);
  else
  {
   stream.append('"');
   
   while( pos != -1 )
   {
    stream.append(cont.substring(ptr,pos+1));
    stream.append('"');

    ptr = pos+1;
    
    if( ptr >= cont.length() )
     break;
    
    pos = cont.indexOf('"',ptr);
   }
   
   stream.append(cont.substring(ptr));
   stream.append('"');
   
  }
  
 }

 @Override
 public void nextCell() throws IOException
 {
  stream.append(sep);
 }

 @Override
 public void nextRow() throws IOException
 {
  stream.append('\n');
  newLine = true;
 }

 
 @Override
 public void start()
 {
 }

 @Override
 public void finish()
 {
 }

}