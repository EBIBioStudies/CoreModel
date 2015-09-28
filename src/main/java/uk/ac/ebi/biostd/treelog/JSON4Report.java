package uk.ac.ebi.biostd.treelog;

import java.io.IOException;

import org.json.JSONObject;

import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class JSON4Report
{
 public static SubmissionReport convert( String text ) throws ConvertException
 {
   JSONObject jo = new JSONObject( text );
   
   return convertJO(jo);
   
 }
  
 public static SubmissionReport convertJO( JSONObject jo ) throws ConvertException
 {
  SubmissionReport rep = new SubmissionReport();
  
  rep.setLog( JSON4Log.convertJO(jo.getJSONObject("log")) );
  
  rep.setSubmissionMappings( JSON4Mapping.convertJO( jo.getJSONArray("mapping") ) );
  
  return rep;
 }
 
 public static void convert( SubmissionReport rep, Appendable out ) throws IOException
 {
  out.append("{\n\"status\": \"");
  
  if( rep.getLog().getLevel().getPriority() < Level.ERROR.getPriority() )
   out.append("OK");
  else
   out.append("FAIL");
   
  out.append("\",\n\"mapping\": ");
  
  JSON4Mapping.convert(rep.getMappings(), out);
  
  out.append(",\n\"log\": ");

  
  JSON4Log.convert(rep.getLog(), out);
  
  out.append("}");
 }
}
