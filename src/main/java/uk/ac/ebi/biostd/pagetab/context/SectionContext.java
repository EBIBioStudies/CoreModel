package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.SectionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SectionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.SectionTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class SectionContext extends BlockContext
{

 private final Section section;
 private final LogNode log;
 

 private AbstractAttribute lastAttr;
 
 private final Matcher nameQualifierMatcher;
 private final Matcher valueQualifierMatcher;
 
 public SectionContext(Section sec, PageTabSyntaxParser2 prs, LogNode sln)
 {
  super(BlockType.SECTION, prs);
  
  section = sec;
  log = sln;
  
  nameQualifierMatcher = PageTabSyntaxParser2.NameQualifierPattern.matcher("");
  valueQualifierMatcher = PageTabSyntaxParser2.ValueQualifierPattern.matcher("");
 }

 public Section getSection()
 {
  return section;
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  SectionAttribute attr = new SectionAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<SectionAttributeTagRef>)tags);

  attr.setHost(section);
  section.addAttribute(attr);
  
  return attr;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return SectionAttributeTagRefFactory.getInstance();
 }

 @Override
 public void parseFirstLine(List<String> cells, int ln)
 {
  String acc = null;
  
  if( cells.size() > 1 )
   acc = cells.get(1).trim();
   
  if( acc != null && acc.length() > 0 )
   section.setAcc( acc );

  if( cells.size() > 2 )
   acc = cells.get(2).trim();
   
  if( acc != null && acc.length() > 0 )
   section.setParentAcc(acc);
 
  section.setAccessTags( getParser().processAccessTags(cells, ln, 4, log) );
  section.setTagRefs( getParser().processTags(cells, ln, 5, SectionTagRefFactory.getInstance(),log) );
 }

 @Override
 public void parseLine(List<String> cells, int lineNo)
 {
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
    lastAttr = addAttribute(atName,val,getParser().processTags(cells, lineNo, 3, SectionAttributeTagRefFactory.getInstance(),log));
   
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
