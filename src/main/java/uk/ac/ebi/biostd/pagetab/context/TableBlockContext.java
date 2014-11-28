package uk.ac.ebi.biostd.pagetab.context;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public abstract class TableBlockContext extends BlockContext
{
 
 
 private final Matcher nameQualifierMatcher;
 private final Matcher valueQualifierMatcher;

 private List<AttrRef> atRefs;
 
 protected TableBlockContext(BlockType typ, PageTabSyntaxParser2 parser, LogNode ln , BlockContext pc )
 {
  super(typ, parser, ln, pc);
  
  nameQualifierMatcher = PageTabSyntaxParser2.NameQualifierPattern.matcher("");
  valueQualifierMatcher = PageTabSyntaxParser2.ValueQualifierPattern.matcher("");
 }

 @Override
 public void parseFirstLine(List<String> parts, int lineNo)
 {
  LogNode log = getContextLogNode();
  
  atRefs = new ArrayList<>( parts.size() );
  
  int emptyIdx=-1;
  
  boolean hasAttr = false;
  
  for(int i=1; i < parts.size(); i++ )
  {
   String nm = parts.get(i).trim();
   
   if( nm.length() == 0 )
   {
    emptyIdx = i+1;
    
    atRefs.add(new AttrRef() );
    
    continue;
   }
   
   if( emptyIdx > 0 )
   {
    log.log(Level.ERROR, "(R" + lineNo + ",C"+emptyIdx+") Missed attribute name");
    emptyIdx = -1;
   }
    
   nameQualifierMatcher.reset(nm);
   
   if( nameQualifierMatcher.matches() )
   {
    log.log(Level.ERROR, "(R" + lineNo + ",C"+emptyIdx+") Attribute name qualifier is not allowed in table blocks");
    atRefs.add(new AttrRef() );
    continue;
   }
   
   valueQualifierMatcher.reset( nm );
  
   AttrRef atr = new AttrRef();
   
   if( valueQualifierMatcher.matches() )
   {
    if( ! hasAttr )
    {
     log.log(Level.ERROR, "(R" + lineNo + ",C"+(i+1)+") Qualifier must follow an attribute");
     nm = null;
    }
    else  
     nm = valueQualifierMatcher.group("name").trim();
    
    atr.classifier=true;
   }
   else
    hasAttr = true;

   atr.name = nm;
   atRefs.add( atr );
  }
  
 }

 @Override
 public void parseLine(List<String> parts, int lineNo)
 {
  LogNode log = getContextLogNode();

  int n = atRefs.size() >= parts.size() - 1?atRefs.size():parts.size() - 1;
  
  AbstractAttribute prevAttr = null;
  
  for( int i=0; i < n; i++ )
  {
   
   AttrRef atr = atRefs.get(i);
   
   if( atr.name == null )
    continue;
   
   String val = parts.get(i+1).trim();
   
   if( val.length() == 0 )
    continue;
   
   if( atr.classifier )
    prevAttr.addValueQualifier( new Qualifier(atr.name,val) );
   else
    prevAttr = addAttribute(atr.name,val,null);
  }
  
  for( int i = atRefs.size()+1; i < parts.size(); i++ )
  {
   if( parts.get(i).trim().length() != 0 )
    log.log(Level.ERROR, "(R" + lineNo + ",C"+(i+1)+") Unexpected value");
  }
  
 }

 static class AttrRef
 {
  String name;
  boolean classifier = false;
 }

}
