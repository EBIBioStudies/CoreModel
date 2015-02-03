package uk.ac.ebi.biostd.out.json;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Annotated;
import uk.ac.ebi.biostd.model.Classified;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SecurityObject;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.out.Formatter;

public class JSONFormatter implements Formatter
{
 public static final String rootSecProperty = "section";
 public static final String attrubutesProperty = "attributes";
 public static final String accNoProperty = "accession";
 public static final String accTagsProperty = "accessTags";
 public static final String classTagsProperty = "classTags";
 public static final String tagProperty = "tag";
 public static final String classifierProperty = "classifier";
 public static final String nameProperty = "name";
 public static final String valueProperty = "value";
 public static final String nmQualProperty = "nmqual";
 public static final String vlQualProperty = "valqual";
 public static final String isRefProperty = "isReference";
 public static final String typeProperty = "type";
 public static final String subsectionsProperty = "subsections";
 public static final String filesProperty = "files";
 public static final String linksProperty = "links";
 public static final String urlProperty = "url";

 @Override
 public void format(Submission s, Appendable out) throws IOException
 {
  JSONArray root = new JSONArray();
  
  
  JSONObject sbm = new JSONObject();
  
  sbm.put(typeProperty, "submission");
  
  if( s.getAccNo() != null )
   sbm.put(accNoProperty, s.getAccNo() );
  
  appendAttributes(sbm,s);
  
  appendAccessTags(sbm,s);
  
  appendTags(sbm,s);

  sbm.put(rootSecProperty, appendSection(new JSONObject(), s.getRootSection()) );
  
  root.put(sbm);
  
  out.append( root.toString(1) );
  
 }

 private JSONObject appendSection(JSONObject jsobj, Section sec)
 {
  jsobj.put(typeProperty, sec.getType() );

  if( sec.getAccNo() != null )
   jsobj.put(accNoProperty, sec.getAccNo() );

  
  appendAttributes(jsobj,sec);
  
  appendAccessTags(jsobj,sec);
  
  appendTags(jsobj,sec);
  
  if( sec.getSections() != null && sec.getSections().size() > 0 )
  {
   JSONArray sbsarr = new JSONArray();
   
   JSONArray sbstblarr = null;
   
   for( Section sbs : sec.getSections() )
   {
    JSONObject jssbs = new JSONObject();
    
    appendSection(jssbs, sbs);
    
    if( sbs.getTableIndex() >=0 )
    {
     if( sbstblarr == null || sbs.getTableIndex() ==0  )
     {
      sbstblarr = new JSONArray();
      sbsarr.put(sbstblarr);
     }

     sbstblarr.put(jssbs);
    }
    else
    {
     sbstblarr = null;
     sbsarr.put(jssbs);
    }
    
   }
   
   jsobj.put(subsectionsProperty, sbsarr);
  }
  
  appendFiles(jsobj, sec);
  appendLinks(jsobj, sec);
  
  return jsobj;
 }
 
 
 private void appendFiles(JSONObject jsobj, Section s)
 {
  if( s.getFileRefs() == null || s.getFileRefs().size() == 0 )
   return;
  
  JSONArray flarr = new JSONArray();
  
  JSONArray fltblarr = null;

  for( FileRef fr : s.getFileRefs() )
  {
   JSONObject jsfl = new JSONObject();
   
   jsfl.put(nameProperty, fr.getName());
   
   appendAttributes(jsfl, fr);
   appendAccessTags(jsfl, fr);
   appendTags(jsfl, fr);
   
   if( fr.getTableIndex() >= 0 )
   {
    if( fltblarr == null || fr.getTableIndex() == 0 )
    {
     fltblarr = new JSONArray();
     flarr.put(fltblarr);
    }
    
    fltblarr.put(jsfl);
   }
   else
   {
    fltblarr = null;
    flarr.put(jsfl);
   }
   
  }
  
  jsobj.put(filesProperty, flarr);
 }
 
 private void appendLinks(JSONObject jsobj, Section s)
 {
  if( s.getLinks() == null || s.getLinks().size() == 0 )
   return;
  
  JSONArray lnarr = new JSONArray();
  
  JSONArray lntblarr = null;

  for( Link ln : s.getLinks() )
  {
   JSONObject jsln = new JSONObject();
   
   jsln.put(urlProperty, ln.getUrl());
   
   appendAttributes(jsln, ln);
   appendAccessTags(jsln, ln);
   appendTags(jsln, ln);
   
   if( ln.getTableIndex() >= 0 )
   {
    if( lntblarr == null || ln.getTableIndex() == 0 )
    {
     lntblarr = new JSONArray();
     lnarr.put(lntblarr);
    }
    
    lntblarr.put(jsln);
   }
   else
   {
    lntblarr = null;
    lnarr.put(jsln);
   }
   
  }
  
  jsobj.put(linksProperty, lnarr);
 }

 private void appendAttributes(JSONObject jsobj, Annotated an)
 {
  if( an.getAttributes() == null ||  an.getAttributes().size() == 0 )
   return;
  
  JSONArray tgarr = new JSONArray();
  
  for( AbstractAttribute aat : an.getAttributes() )
  {
   JSONObject jsat = new JSONObject();

   jsat.put(nameProperty, aat.getName());
   jsat.put(valueProperty, aat.getValue());
   
   if( aat.isReference() )
    jsat.put(isRefProperty, true );
   
   appendTags(jsat, aat);
   
   if( aat.getNameQualifiers() != null && aat.getNameQualifiers().size() != 0 )
   {
    JSONArray nqarr = new JSONArray();
    
    for( Qualifier q : aat.getNameQualifiers() )
    {
     JSONObject nq = new JSONObject();
     
     nq.put(nameProperty, q.getName());
     nq.put(valueProperty, q.getValue());
     
     nqarr.put(nq);
    }
    
    jsat.put(nmQualProperty, nqarr);
   }
   
   if( aat.getValueQualifiers() != null && aat.getValueQualifiers().size() != 0 )
   {
    JSONArray nqarr = new JSONArray();
    
    for( Qualifier q : aat.getValueQualifiers() )
    {
     JSONObject nq = new JSONObject();
     
     nq.put(nameProperty, q.getName());
     nq.put(valueProperty, q.getValue());
     
     nqarr.put(nq);
    }
    
    jsat.put(vlQualProperty, nqarr);
   }
   
   tgarr.put(jsat);
  }
  
  jsobj.put(attrubutesProperty, tgarr);
 }
 
 private void appendAccessTags(JSONObject jsobj, SecurityObject an)
 {
  if( an.getAccessTags() == null || an.getAccessTags().size() == 0 )
   return;
   
  JSONArray tgarr = new JSONArray();
  
  for( AccessTag atg : an.getAccessTags() )
   tgarr.put(atg.getName());
  
  jsobj.put(accTagsProperty, tgarr);
 }
 
 private void appendTags(JSONObject jsobj, Classified an)
 {
  if( an.getTagRefs() == null || an.getTagRefs().size() == 0)
   return;
  
  JSONArray tgarr = new JSONArray();
  
  for( TagRef atg : an.getTagRefs() )
  {
   JSONObject jstr = new JSONObject();
   
   jstr.put(classifierProperty, atg.getTag().getClassifier().getName());
   jstr.put(tagProperty, atg.getTag().getName());
   
   if( atg.getParameter() != null )
    jstr.put(valueProperty, atg.getParameter());
  
   tgarr.put(jstr);
  }
  
  jsobj.put(classTagsProperty, tgarr);
 }

 
}