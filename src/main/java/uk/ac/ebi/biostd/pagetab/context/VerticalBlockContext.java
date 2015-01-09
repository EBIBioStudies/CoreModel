package uk.ac.ebi.biostd.pagetab.context;

import java.util.List;
import java.util.regex.Matcher;

import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Reference;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public abstract class VerticalBlockContext extends BlockContext
{
 protected AbstractAttribute lastAttr;
 
 protected final Matcher nameQualifierMatcher;
 protected final Matcher valueQualifierMatcher;
 protected final Matcher refMatcher;
 
 
 protected VerticalBlockContext(BlockType typ, PageTabSyntaxParser2 parser, LogNode ln, BlockContext pc)
 {
  super(typ, parser, ln, pc);
  
  nameQualifierMatcher = PageTabSyntaxParser2.NameQualifierPattern.matcher("");
  valueQualifierMatcher = PageTabSyntaxParser2.ValueQualifierPattern.matcher("");
  refMatcher = PageTabSyntaxParser2.ReferencePattern.matcher("");
  
 }

 @Override
 public void parseLine(List<String> cells, int lineNo)
 {
  LogNode log = getContextLogNode();

  String atName = cells.get(0);
  
  String val = null;
  
  if( cells.size() > 1 )
  {
   val = cells.get(1).trim();
   
   if( val.length() == 0 )
    val=null;
  }
  
  int nRead=2;
  
  if( val == null )
   log.log(Level.WARN, "(R" + lineNo + ",C2) Empty value");
  
  nameQualifierMatcher.reset(atName);
  
  if( nameQualifierMatcher.matches() )
  {
   atName = nameQualifierMatcher.group("name").trim();
   
   if( lastAttr == null )
    log.log(Level.ERROR, "(R" + lineNo + ",C1) Name qualifier must follow an attribute");
   else
    lastAttr.addNameQualifier(new Qualifier( atName , val ));
  }
  else
  {
   valueQualifierMatcher.reset(atName);
   
   if( valueQualifierMatcher.matches() )
   {
    atName = valueQualifierMatcher.group("name").trim();
    
    if( lastAttr == null )
     log.log(Level.ERROR, "(R" + lineNo + ",C1) Value qualifier must follow an attribute");
    else
     lastAttr.addValueQualifier(new Qualifier( atName , val ));
   }
   else
   {
    refMatcher.reset(atName);
    
    if( refMatcher.matches() )
    {
     atName = refMatcher.group("name").trim();
     
     Reference ref = addReference(atName,val,getParser().processTags(cells, lineNo, 3, getAttributeTagRefFactory(),log));
     
     if( ref == null )
      log.log(Level.ERROR, "(R" + lineNo + ",C1) References are not allowed in this context");
     else
      lastAttr = ref;

    }
    
    lastAttr = addAttribute(atName,val,getParser().processTags(cells, lineNo, 3, getAttributeTagRefFactory(),log));
   }
   
   nRead=3;
  }
  
  if( cells.size() > nRead )
  {
   for( int i = nRead; i < cells.size(); i++ )
    if( cells.get(i).trim().length() != 0 )
     log.log(Level.WARN, "(R" + lineNo + ",C"+(i+1)+") Unexpected value");
  }
  
 }

}
