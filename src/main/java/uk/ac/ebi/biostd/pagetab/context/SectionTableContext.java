package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.SectionAttributeTagRef;
import uk.ac.ebi.biostd.model.SectionRef;
import uk.ac.ebi.biostd.model.trfactory.SectionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.ParserState;
import uk.ac.ebi.biostd.pagetab.SectionOccurance;
import uk.ac.ebi.biostd.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class SectionTableContext extends TableBlockContext
{

 private final String secName;
 private final SectionOccurance parent;
 
 private Section current;
 private int tableIdx=-1;
 
 public SectionTableContext(String sName, SectionOccurance parentSec, SubmissionInfo si, ParserState prs, LogNode sln)
 {
  super(BlockType.SECTABLE, si, prs,sln);
  
  secName = sName;
  parent = parentSec;
 }

 @SuppressWarnings("unchecked")
 @Override
 public AbstractAttribute addAttribute(String nm, String val, Collection< ? extends TagRef> tags)
 {
  SectionAttribute attr = new SectionAttribute();
  
  attr.setName(nm);
  attr.setValue(val);

  attr.setTagRefs((Collection<SectionAttributeTagRef>)tags);

  attr.setHost(current);
  current.addAttribute(attr);
  
  return attr;
 }

 @Override
 public TagReferenceFactory< ? > getAttributeTagRefFactory()
 {
  return SectionAttributeTagRefFactory.getInstance();
 }


 @Override
 public void parseLine(List<String> parts, int lineNo)
 {
  LogNode log = getContextLogNode();

  tableIdx++;
  String acc = parts.get(0).trim();
  
  if( acc.length() == 0 )
   acc= null;
  
  current = new Section();
  
  current.setAccNo( acc );
  current.setType(secName);
  current.setTableIndex(tableIdx);
  
  super.parseLine(parts, lineNo);

  if( parent != null )
   parent.getSection().addSection(current);
  
  if(acc != null)
  {
   Matcher genAccNoMtch = getParserState().getGeneratedAccNoMatcher();
   
   genAccNoMtch.reset( acc );
   
   SectionRef sr = new SectionRef(current);
   
   if( genAccNoMtch.matches() )
   {
    sr.setLocal(false);

    String pfx = genAccNoMtch.group("pfx");
    String sfx = genAccNoMtch.group("sfx");
    
    sr.setPrefix(pfx);
    sr.setSuffix(sfx);
    
    sr.setAccNo(genAccNoMtch.group("tmpid"));
    current.setAccNo(sr.getAccNo());
    
    boolean gen=false;
    
    if( pfx != null && pfx.length() > 0 )
    { 
     gen = true;
     
     if( Character.isDigit( pfx.charAt(pfx.length()-1) ) )
      log.log(Level.ERROR, "(R" + lineNo + ",C2) Accession number prefix can't end with a digit '" + pfx + "'");
    }
    
    if( sfx != null && sfx.length() > 0 ) 
    { 
     gen = true;
     
     if( Character.isDigit( sfx.charAt(0) ) )
      log.log(Level.ERROR, "(R" + lineNo + ",C2) Accession number suffix can't start with a digit '" + sfx + "'");
    }
    
    if( gen )
     getSubmissionInfo().addSec2genId(sr);
   }
   else
    current.setAccNo(acc);

   if( current.getAccNo() != null  )
   {
    SectionOccurance secOc = getSubmissionInfo().getSectionOccurance( current.getAccNo() );
    
    if( secOc == null )
    {
     secOc = new SectionOccurance();
     
     secOc.setRow(lineNo);
     secOc.setCol(1);
     secOc.setSection(current);
     secOc.setSecLogNode(log);
     
     getSubmissionInfo().addSectionOccurance(secOc);
    }
    else
     log.log(Level.ERROR, "Accession number '"+current.getAccNo()+"' is used by other section at (R" + secOc.getRow() + ",C"+secOc.getCol()+")");
   }
   
  }
  
 }

 
}
