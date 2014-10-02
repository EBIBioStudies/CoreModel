package uk.ac.ebi.biostd.pagetab.context;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.biostd.authz.TagRef;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.SectionAttributeTagRef;
import uk.ac.ebi.biostd.model.trfactory.SectionAttributeTagRefFactory;
import uk.ac.ebi.biostd.model.trfactory.TagReferenceFactory;
import uk.ac.ebi.biostd.pagetab.PageTabSyntaxParser2;
import uk.ac.ebi.biostd.pagetab.SectionRef;
import uk.ac.ebi.biostd.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.treelog.LogNode;
import uk.ac.ebi.biostd.treelog.LogNode.Level;

public class SectionTableContext extends TableBlockContext
{

 private final String secName;
 private final Section parent;
 private SubmissionInfo submInf;
 
 private Section current;
 private int tableIdx=-1;
 
 private Matcher genAccNoMtch;
 
 public SectionTableContext(String sName, Section pSec, SubmissionInfo si, PageTabSyntaxParser2 prs, LogNode sln)
 {
  super(BlockType.SECTABLE,prs,sln);
  
  secName = sName;
  parent = pSec;
  
  submInf = si;
  
  genAccNoMtch = Pattern.compile(PageTabSyntaxParser2.GeneratedAccNoRx).matcher("");
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
   parent.addSection(current);
  
  if(acc != null)
  {
   genAccNoMtch.reset( acc );
   
   SectionRef sr = new SectionRef(current);
   
   if( genAccNoMtch.matches() )
   {
    sr.setLocal(false);
    sr.setPrefix(genAccNoMtch.group("pfx"));
    sr.setSuffix(genAccNoMtch.group("sfx"));
    
    sr.setAccNo(genAccNoMtch.group("tmpid"));
    current.setAccNo(sr.getAccNo());
   }
   else
    current.setAccNo(null);

   if( sr.getAccNo() != null && sr.getAccNo().length() > 0 )
   {
    if(submInf.getSectionMap().containsKey(sr.getAccNo()))
     log.log(Level.ERROR, "(R" + lineNo + ",C2) Section accession number '" + sr.getAccNo() + "' is arleady used for another object");
    else
     submInf.getSectionMap().put(sr.getAccNo(), sr);
   }
   
  }
  
 }

 
}