package uk.ac.ebi.biostd.treelog;

import java.io.IOException;

import org.json.JSONObject;

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
  out.append("{\nmapping: ");
  
  JSON4Mapping.convert(rep.getMappings(), out);
  
  out.append(",\nlog: ");

  
  JSON4Log.convert(rep.getLog(), out);
  
  out.append("}");
 }
}
