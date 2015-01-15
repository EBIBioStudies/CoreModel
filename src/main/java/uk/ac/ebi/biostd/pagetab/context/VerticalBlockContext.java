package uk.ac.ebi.biostd.pagetab.context;

import java.util.List;
import java.util.regex.Matcher;

import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Reference;
import uk.ac.ebi.biostd.pagetab.ParserState;
import uk.ac.ebi.biostd.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public abstract class VerticalBlockContext extends BlockContext
{
 protected AbstractAttribute lastAttr;
 
 protected final Matcher nameQualifierMatcher;
 protected final Matcher valueQualifierMatcher;
 protected final Matcher referenceMatcher;
 
 private SubmissionInfo submInfo;
 
 protected VerticalBlockContext(BlockType typ, SubmissionInfo si, ParserState ps, LogNode ln )
 {
  super( typ, ps, ln );
  
  nameQualifierMatcher = ps.getNameQualifierMatcher();
  valueQualifierMatcher = ps.getValueQualifierMatcher();
  referenceMatcher = ps.getReferenceMatcher();
  
  submInfo = si;
 }

 public SubmissionInfo getSubmissionInfo()
 {
  return submInfo;
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
    referenceMatcher.reset(atName);
    
    if( referenceMatcher.matches() )
    {
     atName = referenceMatcher.group("name").trim();
     
     Reference ref = addReference(atName,val,getParserState().getParser().processTags(cells, lineNo, 3, getAttributeTagRefFactory(),log));
     
     if( ref == null )
      log.log(Level.ERROR, "(R" + lineNo + ",C1) References are not allowed in this context");
     else
     {
      lastAttr = ref;
     
      submInfo.addReferenceOccurance(lineNo, 2, val, log);
     }
    }
    else
     lastAttr = addAttribute(atName,val,getParserState().getParser().processTags(cells, lineNo, 3, getAttributeTagRefFactory(),log));
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
