package uk.ac.ebi.biostd.treelog;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class JSON2Log
{
 public static LogNode convert( String text ) throws ConvertException 
 {
  JSONObject jo = new JSONObject( text );
  
  return convertJO(jo);
  
 }
 
 private static LogNode convertJO( JSONObject jo ) throws ConvertException
 {
  String lvlStr = jo.getString("level");
  
  if( lvlStr == null )
   throw new ConvertException("'level' property missing");

  Level lvl = null;
  
  try
  {
   lvl = Level.valueOf(lvlStr);
  }
  catch( Throwable t )
  {}
  
  if( lvl == null )
   throw new ConvertException("Invalid 'level' property value: "+lvlStr);
  
  String msg = jo.getString("message");
  
  if( msg == null )
   throw new ConvertException("'message' property missing");
  
  SimpleLogNode sln = new SimpleLogNode(lvl,msg, null);
  
  JSONArray subnodes = jo.optJSONArray("subnodes");
  
  if( subnodes != null )
  {
   for( int i=0; i < subnodes.length(); i++ )
    sln.append( convertJO(subnodes.getJSONObject(i)) );
  }  

  return sln;
  
 }  
}
