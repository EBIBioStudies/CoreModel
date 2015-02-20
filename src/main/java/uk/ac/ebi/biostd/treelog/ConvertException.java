package uk.ac.ebi.biostd.treelog;

public class ConvertException extends Exception
{
 public ConvertException()
 {
 }
 
 public ConvertException( String msg )
 {
  super(msg);
 }
 
 public ConvertException( String msg, Throwable cause )
 {
  super(msg, cause);
 }
 
}
