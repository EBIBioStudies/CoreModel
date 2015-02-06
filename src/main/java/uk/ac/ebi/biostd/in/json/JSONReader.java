package uk.ac.ebi.biostd.in.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.Tag;
import uk.ac.ebi.biostd.db.TagResolver;
import uk.ac.ebi.biostd.in.Parser;
import uk.ac.ebi.biostd.in.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Classified;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Node;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.out.json.JSONFormatter;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;
import uk.ac.ebi.biostd.treelog.SimpleLogNode;

public class JSONReader extends Parser
{
 interface NodeProcessor<NT>
 {
  boolean process( Object jsno, NT nd, LogNode ln, Stack<String> path);
 }
 
 private TagResolver tagResolver;
 private boolean strictTags;
 
 public JSONReader( TagResolver tgResl, boolean stctTags )
 {
  tagResolver = tgResl;
  strictTags = stctTags;
 }
 
 @Override
 public List<SubmissionInfo> parse( String txt, LogNode rln )
 {
  
  LogNode sln = rln.branch("Parsing JSON body");
  
  JSONArray sbmarr = null;
  
  try
  {
   sbmarr = new JSONArray(txt);
  }
  catch(JSONException e)
  {
   sln.log(Level.ERROR, "Parsing failed: "+e.getMessage());
   return null;
  }
  
  List<SubmissionInfo> subms = new ArrayList<SubmissionInfo>();
  
  Stack<String> path = new Stack<String>();
  
  path.push("");
  
  for( int i=0; i < sbmarr.length(); i++ )
  {
   try
   {
    path.push(String.valueOf(i));
        
    Object o = sbmarr.get(i);
    
    if( ! (o instanceof JSONObject) )
    {
     sln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: expected JSON object");
     return null;
    }
    
    subms.add( processSubmission( (JSONObject)o, rln, path ) );
    
   }
   finally
   {
    path.pop();
   }
   
  }
  
  SimpleLogNode.setLevels(rln);
  
  
  return subms;
  
 }
 
 
 private SubmissionInfo processSubmission( JSONObject obj, LogNode ln, Stack<String> path )
 {
  Submission sbm = new Submission();
  
  SubmissionInfo si = new SubmissionInfo(sbm);
  
  ln = ln.branch("Procesing submission");
  
  Iterator<String> kitr = obj.keys();
  
  boolean typeOk=false;
  boolean rootOk=false;

  while( kitr.hasNext() )
  {
   String key = kitr.next();
   Object val = obj.get(key);

      
   try
   {
    path.push(key);

    switch(key)
    {
     case JSONFormatter.typeProperty:

      typeOk = true;

      if(!(val instanceof String))
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid value. String expected");
       continue;
      }
      
      if( ! "submission".equals( val) )
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: type '"+val+"' isn't expected here. Must be 'submission'");
       continue;
      }
      
      
      break;

     case JSONFormatter.rootSecProperty:
      
      rootOk = true;
      
      if( !(val instanceof JSONObject ) ) 
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: section object expected here");
       continue;
      }
      
      sbm.setRootSection( processSection( (JSONObject)val, ln, path ) );
      
      break;

     case JSONFormatter.accNoProperty:
      
      if(!(val instanceof String))
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid value. String expected");
       continue;
      }

      sbm.setAccNo(val.toString());
      break;
      
     default:
      
      if( ! processCommon(key,val,sbm,ln,path) )
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid property");
       continue;
      }
      
      break;
    }
    
    
   }
   finally
   {
    path.pop();
   }
  }
  
  if( ! typeOk )
   ln.log(Level.WARN, "Object missing 'type' property. 'submission' assumed");
  
  if( ! rootOk )
   ln.log(Level.ERROR, "Submission missing root section");
  
  
  return si;
 }
 
 
 <NT> boolean processArray( Object val, NT nd, LogNode ln, Stack<String> path, NodeProcessor<NT> np )
 {
  if( !(val instanceof JSONArray ) ) 
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: array expected here");
   return false;
  }
  
  boolean res = true;
  
  for( int j=0; j < ((JSONArray)val).length(); j++ )
  {
   try
   {
    path.push(String.valueOf(j));
    
    Object sso = ((JSONArray) val).get(j);

//    if( !( sso instanceof JSONObject ) )
//    {
//     ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: JSON object expected here" );
//     continue;
//    }
     
    res = res && np.process(sso,nd,ln,path);

   }
   finally
   {
    path.pop();
   }
  }
  
  return res;
 }
 
 private boolean processCommon(String key, Object val, Node obj, LogNode ln, Stack<String> path)
 {
  switch(key)
  {
   case JSONFormatter.attrubutesProperty:
    
    processArray(val, obj, ln, path, (v, o, l, p) -> processAttribute(v, o, l, p) );
    
    break;

   case JSONFormatter.classTagsProperty:
    
    processArray(val, obj, ln, path, (v, o, l, p) -> processClassTag(v, o, l, p) );
    
    break;

   case JSONFormatter.accTagsProperty:
    
     processArray(val, obj, ln, path, (v, o, l, p) -> processAccessTag(v, o, l, p) );
    
    break;

    
   default:
    return false;
  }
  
  return true;
 }

 private Qualifier processQualifier( Object tgjo, LogNode ln, Stack<String> path )
 {
  if(!(tgjo instanceof JSONObject))
  {
   ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: JSON object expected here");
   return null;
  }
  
  Iterator<String> kitr = ((JSONObject)tgjo).keys();

  boolean res = true;
  
  Qualifier q = new Qualifier();
  
  while(kitr.hasNext())
  {
   String key = kitr.next();
   Object val = ((JSONObject)tgjo).get(key);
   try
   {
    path.push(key);

    
    switch(key)
    {
     case JSONFormatter.nameProperty:
      
      if( ! (val instanceof String ) )
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: string value expected" );
       res = false;
      }
      else
       q.setName((String)val);
      
      break;
     
     case JSONFormatter.valueProperty:
      
      if( ! (val instanceof String ) )
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: string value expected" );
       res = false;
      }
      else
       q.setValue((String)val);
      
      break;
      
     default:

       ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: invalid property '"+key+"'");
       res = false;
      break;
    }

   }
   finally
   {
    path.pop();
   }
  }
  
  if( q.getName() == null )
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: '"+JSONFormatter.nameProperty+"' property missing");
   res = false;
  }
  
  if( q.getValue() == null )
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: '"+JSONFormatter.valueProperty+"' property missing");
   res = false;
  }
  
  if( ! res )
   return null;
  
  return q;
 }
 
 private boolean  processNameQualifier(Object val, AbstractAttribute at, LogNode ln, Stack<String> path)
 {
  Qualifier q = processQualifier(val, ln, path);
  
  if( q == null )
   return false;
  
  at.addNameQualifier(q);
  
  return true;
 }
 
 private boolean  processValueQualifier(Object val, AbstractAttribute at, LogNode ln, Stack<String> path)
 {
  Qualifier q = processQualifier(val, ln, path);
  
  if( q == null )
   return false;
  
  at.addValueQualifier(q);
  
  return true;
 }

 private boolean processAccessTag(Object val, Node nd, LogNode ln, Stack<String> path)
 {
  if( !( val instanceof String ))
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: string value expected" );
   return false;
  }
  
  AccessTag at = tagResolver.getAccessTagByName((String)val);
  
  if( at == null )
  {
   ln.log(strictTags?Level.ERROR:Level.WARN, "Path '"+pathToString(path)+"' error: access tag '"+val+"' can't be resolved" );
  
   return ! strictTags;
  }
  else
  {
//   ln.log(Level.INFO, "Access tag '" +val+"' resolved");

   nd.addAccessTag(at);
  }
  
  return true;
 }
 
 private boolean processAttribute(Object tgjo, Node nd, LogNode ln, Stack<String> path)
 {
  if(!(tgjo instanceof JSONObject))
  {
   ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: JSON object expected here");
   return false;
  }
  
  Iterator<String> kitr = ((JSONObject)tgjo).keys();

  boolean res = true;
  
  AbstractAttribute atr = nd.addAttribute(null, null);

  while(kitr.hasNext())
  {
   String key = kitr.next();
   Object val = ((JSONObject)tgjo).get(key);
   try
   {
    path.push(key);

    
    switch(key)
    {
     case JSONFormatter.nameProperty:
      
      if( ! (val instanceof String ) )
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: string value expected" );
       res = false;
      }
      else
       atr.setName((String)val);
      
      break;
     
     case JSONFormatter.valueProperty:
      
      if( ! (val instanceof String ) )
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: string value expected" );
       res = false;
      }
      else
       atr.setValue((String)val);
      
      break;
      
     case JSONFormatter.isRefProperty:
      
      if( val instanceof Boolean )
       atr.setReferenece((Boolean)val);
      else if( val instanceof String )
      {
       if( "true".equalsIgnoreCase((String)val) )
        atr.setReferenece(true);
       else if( "false".equalsIgnoreCase((String)val) )
        atr.setReferenece(false);
       else
       {
        ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid value. Boolean expected" );
        res = false;
       }
      }
      else
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid value. Boolean expected" );
       res = false;
      }
       
     
     case JSONFormatter.classTagsProperty:
      
      res = res && processArray(val, atr, ln, path, (v, o, l, p) -> processClassTag(v, o, l, p) );
      
      break;
      
     case JSONFormatter.nmQualProperty:
      
      res = res && processArray(val, atr, ln, path, (v, o, l, p) -> processNameQualifier(v, o, l, p) );
      
      break;

     case JSONFormatter.vlQualProperty:
      
      res = res && processArray(val, atr, ln, path, (v, o, l, p) -> processValueQualifier(v, o, l, p) );
      
      break;
  
     default:

       ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: invalid property '"+key+"'");
       res = false;
      break;
    }

   }
   finally
   {
    path.pop();
   }
  }
  
  if( atr.getName() == null )
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: '"+JSONFormatter.nameProperty+"' property missing");
   res = false;
  }
  
  if( atr.getValue() == null )
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: '"+JSONFormatter.valueProperty+"' property missing");
   res = false;
  }
  
  if( ! res )
   nd.removeAttribute(atr);
  
  return res;
 }


 boolean processClassTag(Object tgjo, Classified nd, LogNode ln, Stack<String> path)
 {
  if(!(tgjo instanceof JSONObject))
  {
   ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: JSON object expected here");
   return false;
  }
  
//  ln = ln.branch("Processing tag");

  Iterator<String> kitr = ((JSONObject)tgjo).keys();

  String tagName = null;
  String clsfrName = null;
  String tgVal = null;

  while(kitr.hasNext())
  {
   String key = kitr.next();
   Object val = ((JSONObject)tgjo).get(key);
   try
   {
    path.push(key);

    if( ! (val instanceof String ) )
    {
     ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: string value expected" );
     return false;
    }
    
    switch(key)
    {
     case JSONFormatter.tagProperty:
      
      tagName = (String)val;
      
      break;
     
     case JSONFormatter.classifierProperty:
      
      clsfrName = (String)val;
      
      break;
     
     case JSONFormatter.valueProperty:
      
      tgVal = (String)val;
      
      break;
      
     default:

       ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: invalid property '"+key+"'");
      break;
    }

   }
   finally
   {
    path.pop();
   }
  }
  
  if( tagName == null )
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: '"+JSONFormatter.tagProperty+"' property missing");
   return false;
  }

  if( clsfrName == null )
  {
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: '"+JSONFormatter.classifierProperty+"' property missing");
   return false;
  }

  Tag tg = tagResolver.getTagByName(clsfrName, tagName);
  
  if(tg == null)
   ln.log(strictTags ? Level.ERROR : Level.WARN, "Path '" + pathToString(path) + "' error: tag '" + clsfrName +":"+tagName+"' can't be resolved");
  else
  {
//   ln.log(Level.INFO, "Tag '" + clsfrName +":"+tagName+"' resolved");
   
   nd.addTagRef(tg, tgVal);
  }
  
  return true;
 }

 
 private FileRef processFile(JSONObject obj, LogNode ln, Stack<String> path)
 {
  FileRef fr = new FileRef();

  ln = ln.branch("Processing file reference");

  Iterator<String> kitr = obj.keys();

  boolean nameOk=false;

  while(kitr.hasNext())
  {
   String key = kitr.next();
   Object val = obj.get(key);
   try
   {
    path.push(key);

    switch(key)
    {
     case JSONFormatter.nameProperty:
      
      nameOk = true;
      
      if( ! (val instanceof String) )
      {
       ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: string value expected");
       continue;
      }
      
      fr.setName( (String) val );
      
      break;
     
     default:

      if(!processCommon(key, val, fr, ln, path))
      {
       ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: invalid property '"+key+"'");
       continue;
      }

      break;
    }

   }
   finally
   {
    path.pop();
   }
  }
  
  if( ! nameOk )
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' Object missing '"+JSONFormatter.nameProperty+"' property");
  
  return fr;
 }
 
 private Link processLink(JSONObject obj, LogNode ln, Stack<String> path)
 {
  Link lnk = new Link();
  
  ln = ln.branch("Processing link");

  Iterator<String> kitr = obj.keys();

  boolean urlOk=false;

  while(kitr.hasNext())
  {
   String key = kitr.next();
   Object val = obj.get(key);
   try
   {
    path.push(key);

    switch(key)
    {
     case JSONFormatter.urlProperty:
      
      urlOk = true;
      
      if( ! (val instanceof String) )
      {
       ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: string value expected");
       continue;
      }
      
      lnk.setUrl( (String) val );
      
      break;
     
     default:

      if(!processCommon(key, val, lnk, ln, path))
      {
       ln.log(Level.ERROR, "Path '" + pathToString(path) + "' error: invalid property '"+key+"'");
       continue;
      }

      break;
    }

   }
   finally
   {
    path.pop();
   }
  }
  
  if( ! urlOk )
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' Object missing '"+JSONFormatter.urlProperty+"' property");
  
  return lnk;
 }

 
 private Section processSection(JSONObject obj, LogNode ln, Stack<String> path)
 {
  Section sec = new Section();
  
  ln = ln.branch("Processing section");
  
  Iterator<String> kitr = obj.keys();
  
  boolean typeOk=false;

  while( kitr.hasNext() )
  {
   String key = kitr.next();
   Object val = obj.get(key);

      
   try
   {
    path.push(key);

    switch(key)
    {
     case JSONFormatter.typeProperty:

      typeOk = true;

      if(!(val instanceof String))
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid value. String expected");
       continue;
      }
      
      sec.setType((String)val);
      
      break;

     case JSONFormatter.accNoProperty:
      
      if(!(val instanceof String))
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid value. String expected");
       continue;
      }
      
      sec.setAccNo(val.toString());
      break;

     case JSONFormatter.subsectionsProperty:
      
      
      if( !(val instanceof JSONArray ) ) 
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: array expected here");
       continue;
      }
      
      
      for( int j=0; j < ((JSONArray)val).length(); j++ )
      {
       try
       {
        path.push(String.valueOf(j));
        
        Object sso = ((JSONArray) val).get(j);

        if(sso instanceof JSONObject)
         sec.addSection(processSection((JSONObject) sso, ln, path));
        else if(sso instanceof JSONArray)
        {
         for(int k = 0; k < ((JSONArray) sso).length(); k++)
         {
          try
          {
           path.push(String.valueOf(k));
           
           Object tsso = ((JSONArray) sso).get(k);
           
           if( ! (tsso instanceof JSONObject ) )
            ln.log(Level.ERROR, "Path '"+pathToString(path)+"' JSON object expected" );
           
           Section ss = processSection((JSONObject) tsso, ln, path);
           
           if( ss != null )
           {
            ss.setTableIndex(k);
            sec.addSection(ss);
           }
           
          }
          finally
          {
           path.pop();
          }
          
         }
        }
        else
         ln.log(Level.ERROR, "Path '"+pathToString(path)+"' unexpected class: "+sso.getClass().getName() );
       }
       finally
       {
        path.pop();
       }
      }
      
      break;

     case JSONFormatter.linksProperty:
      
      if( !(val instanceof JSONArray ) ) 
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: array expected here");
       continue;
      }
      
      for( int j=0; j < ((JSONArray)val).length(); j++ )
      {
       try
       {
        path.push(String.valueOf(j));
        
        Object sso = ((JSONArray) val).get(j);

        if(sso instanceof JSONObject)
         sec.addLink(processLink((JSONObject) sso, ln, path));
        else if(sso instanceof JSONArray)
        {
         for(int k = 0; k < ((JSONArray) sso).length(); k++)
         {
          try
          {
           path.push(String.valueOf(k));
           
           Object tsso = ((JSONArray) sso).get(k);
           
           if( ! (tsso instanceof JSONObject ) )
            ln.log(Level.ERROR, "Path '"+pathToString(path)+"' JSON object expected" );
           
           Link lnk = processLink((JSONObject) tsso, ln, path);
           
           if( lnk != null )
           {
            lnk.setTableIndex(k);
            sec.addLink(lnk);
           }
           
          }
          finally
          {
           path.pop();
          }
          
         }
        }
        else
         ln.log(Level.ERROR, "Path '"+pathToString(path)+"' unexpected class: "+sso.getClass().getName() );
       }
       finally
       {
        path.pop();
       }
      }
      
     
      break;

      
     case JSONFormatter.filesProperty:

      if( !(val instanceof JSONArray ) ) 
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: array expected here");
       continue;
      }
      
      for( int j=0; j < ((JSONArray)val).length(); j++ )
      {
       try
       {
        path.push(String.valueOf(j));
        
        Object sso = ((JSONArray) val).get(j);

        if(sso instanceof JSONObject)
         sec.addFileRef(processFile((JSONObject) sso, ln, path));
        else if(sso instanceof JSONArray)
        {
         for(int k = 0; k < ((JSONArray) sso).length(); k++)
         {
          try
          {
           path.push(String.valueOf(k));
           
           Object tsso = ((JSONArray) sso).get(k);
           
           if( ! (tsso instanceof JSONObject ) )
            ln.log(Level.ERROR, "Path '"+pathToString(path)+"' JSON object expected" );
           
           FileRef fr = processFile((JSONObject) tsso, ln, path);
           
           if( fr != null )
           {
            fr.setTableIndex(k);
            sec.addFileRef(fr);
           }
           
          }
          finally
          {
           path.pop();
          }
          
         }
        }
        else
         ln.log(Level.ERROR, "Path '"+pathToString(path)+"' unexpected class: "+sso.getClass().getName() );
       }
       finally
       {
        path.pop();
       }
      }
      
     
      break;
      
     default:
      
      if( ! processCommon(key,val,sec,ln,path) )
      {
       ln.log(Level.ERROR, "Path '"+pathToString(path)+"' error: invalid property '"+key+"'");
       continue;
      }
      
      break;
    }
    
    
   }
   finally
   {
    path.pop();
   }
  }
  
  if( ! typeOk )
   ln.log(Level.ERROR, "Path '"+pathToString(path)+"' Object missing '"+JSONFormatter.typeProperty+"' property");
  
  
  
  return sec;
 }


 private String pathToString( Stack<String> pth )
 {
  StringBuilder sb = new StringBuilder(200);
  
  for( int i=0; i < pth.size(); i++ )
   sb.append("/").append(pth.get(i) );
  
  return sb.toString();
 }
}
