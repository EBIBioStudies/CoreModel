package uk.ac.ebi.biostd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import uk.ac.ebi.biostd.authz.TagRef;

@MappedSuperclass
abstract public class AbstractAttribute
{
 private final static String QUALIFIERS_SEPARATOR = ";";
 private final static String QUALIFIER_VALUE_SEPARATOR = "=";
 
 private static final Pattern qStrSplit = Pattern.compile("(?<!\\\\)"+QUALIFIERS_SEPARATOR);
 private static final Pattern unescQS = Pattern.compile("\\\\"+QUALIFIERS_SEPARATOR);
 private static final Pattern unescQV = Pattern.compile("\\\\"+QUALIFIER_VALUE_SEPARATOR);
 private static final Pattern escQS = Pattern.compile(QUALIFIERS_SEPARATOR);
 private static final Pattern escQV = Pattern.compile(QUALIFIER_VALUE_SEPARATOR);
 
 private Collection<Qualifier> quals;
 
 @Lob
 @Basic
 private String qualifierString;
 
 public AbstractAttribute()
 {}

 public AbstractAttribute(String name2, String value2)
 {
  setName(name2);
  setValue(value2);
 }
 
 
 @Id
 @GeneratedValue
 public long getId()
 {
  return id;
 }
 private long id;
  
 public void setId(long id)
 {
  this.id = id;
 }

 public String getName()
 {
  return name;
 }
 String name;

 public void setName(String name)
 {
  this.name = name;
 }
 
 public Collection<Qualifier> getQualifiers()
 {
  if( quals != null )
   return quals;
  
  if( qualifierString == null )
   return null;
  
  String[] qus = qStrSplit.split(qualifierString);
  
  quals = new ArrayList<>(qus.length);
  
  for( String s : qus )
  {
   s = unescQS.matcher(s).replaceAll(QUALIFIERS_SEPARATOR);
   
   String nm = s;
   String vl = null;
   
   int pos = 0;
   
   while( pos < s.length() && ( pos = s.indexOf(QUALIFIER_VALUE_SEPARATOR,pos) ) != -1 )
   {
    if( pos == 0 )
     break;
    
    if( s.charAt(pos-1) != '\\' )
    {
     nm = unescQV.matcher(s.substring(0,pos)).replaceAll(QUALIFIER_VALUE_SEPARATOR);
     vl = s.substring(pos+QUALIFIER_VALUE_SEPARATOR.length());

     break;
    }
    
    pos = pos + QUALIFIER_VALUE_SEPARATOR.length();
   }
   
   quals.add( new Qualifier(nm,vl) );
  }

  
  return quals;
 }
 

 public void setNameQualifiers( Collection<Qualifier> qs )
 {
  quals = qs;
  
  if( qs == null || qs.size() == 0 )
   return;
  
  StringBuilder sb = new StringBuilder();
  
  Matcher qvMatcher = escQV.matcher("");
  Matcher qsMatcher = escQS.matcher("");
  
  for( Qualifier q : qs )
  {
   sb.append( qsMatcher.reset( qvMatcher.reset(q.getName()).replaceAll("\\\\"+QUALIFIER_VALUE_SEPARATOR) ).replaceAll("\\\\"+QUALIFIERS_SEPARATOR) );
   sb.append(QUALIFIER_VALUE_SEPARATOR);
   sb.append( qsMatcher.reset( q.getValue() ).replaceAll(QUALIFIERS_SEPARATOR) );
   sb.append(QUALIFIERS_SEPARATOR);
  }
  
  sb.setLength(sb.length() - QUALIFIERS_SEPARATOR.length() );
  
  qualifierString = sb.toString();
 }
 
 public void addQualifier( Qualifier q )
 {
  StringBuilder sb = new StringBuilder();
  
  Matcher qvMatcher = escQV.matcher("");
  Matcher qsMatcher = escQS.matcher("");
  
  if( qualifierString != null && qualifierString.length() > 0 )
   sb.append(qualifierString).append(QUALIFIERS_SEPARATOR);
  
  sb.append( qsMatcher.reset( qvMatcher.reset(q.getName()).replaceAll("\\\\"+QUALIFIER_VALUE_SEPARATOR) ).replaceAll("\\\\"+QUALIFIERS_SEPARATOR) );
  sb.append(QUALIFIER_VALUE_SEPARATOR);
  sb.append( qsMatcher.reset( q.getValue() ).replaceAll("\\\\"+QUALIFIERS_SEPARATOR) );

  qualifierString = sb.toString();
  
  if( quals == null )
   quals = new ArrayList<>();
   
  quals.add( q );
 }

 
 public String getValue()
 {
  return value;
 }
 String value;

 public void setValue(String value)
 {
  this.value = value;
 }

 public String getValueQualifier()
 {
  return valueQualifier;
 }
 String valueQualifier;

 public void setValueQualifier(String value)
 {
  this.valueQualifier = value;
 }

 
 public double getNumValue()
 {
  return numValue;
 }
 double numValue;

 public void setNumValue(double numValue)
 {
  this.numValue = numValue;
 }

 @Transient
 public abstract Collection<? extends TagRef> getTagRefs();
 
 @Transient
 public String getEntityClass()
 {
  if( getTagRefs() == null )
   return null;
  
  StringBuilder sb = new StringBuilder();
  
  for( TagRef t : getTagRefs() )
  {
   sb.append(t.getTag().getClassifier().getName()).append(":").append(t.getTag().getName());
   
   if( t.getParameter() != null && t.getParameter().length() != 0 )
    sb.append("=").append( t.getParameter() );
   
   sb.append(",");
  }
  
  if( sb.length() > 0 )
   sb.setLength( sb.length()-1 );
  
  return sb.toString();
 }
}
