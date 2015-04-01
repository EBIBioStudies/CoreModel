package uk.ac.ebi.biostd.out.cell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.biostd.in.pagetab.PageTabElements;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Annotated;
import uk.ac.ebi.biostd.model.Classified;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Node;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SecurityObject;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.out.Formatter;

public class CellFormatter implements Formatter
{
 private CellStream cstr ;
 private int counter=1;

 @Override
 public void header(Map<String, List<String>> hdrs, Appendable out) throws IOException
 {
  
  if( hdrs == null )
   return;
  
  for( Map.Entry<String, List<String>> me : hdrs.entrySet() )
  {
   for( String val : me.getValue() )
   {
    cstr.addCell(PageTabElements.DocParamPrefix+me.getKey());
    cstr.addCell(val);
    cstr.nextRow();
   }
  }
  
 }

 @Override
 public void footer(Appendable out) throws IOException
 {
 }

 @Override
 public void format(Submission s, Appendable out) throws IOException
 {
  cstr.nextRow();
  cstr.addCell(PageTabElements.SubmissionKeyword);
  cstr.addCell(s.getAccNo().startsWith("!")?s.getAccNo():("!"+s.getAccNo()));

  exportNodeTags(s);
  
  exportAnnotation(s);
  
  exportSection(s.getRootSection());
  
 }

 private void exportNodeTags( Node nd )
 {
  if( ( nd.getAccessTags() == null || nd.getAccessTags().size() == 0 ) && (nd.getTagRefs() == null || nd.getTagRefs().size() == 0 ))
   return;
  
  exportAccessTags(nd);
  exportClassifTags(nd);

 }
 
 private void exportAccessTags( SecurityObject so )
 {}
 
 private void exportClassifTags( Classified clsf )
 {}

 private void exportAnnotation(Annotated ant )
 {
  for(AbstractAttribute attr : ant.getAttributes() )
  {
   cstr.nextRow();
   cstr.addCell(attr.getName());
   cstr.addCell(attr.getValue());
   
   if( attr.getTagRefs() != null )
    exportClassifTags(attr);
   
   if( attr.getNameQualifiers() != null )
   {
    for( Qualifier q : attr.getNameQualifiers() )
    {
     cstr.nextRow();
     cstr.addCell(Character.toString(PageTabElements.NameQOpen)+q.getName()+Character.toString(PageTabElements.NameQClose));
     cstr.addCell(q.getValue());
    }
   }
 
   if( attr.getValueQualifiers() != null )
   {
    for( Qualifier q : attr.getValueQualifiers() )
    {
     cstr.nextRow();
     cstr.addCell(Character.toString(PageTabElements.ValueQOpen)+q.getName()+Character.toString(PageTabElements.ValueQClose));
     cstr.addCell(q.getValue());
    }
   }
   
  }
  
 }
 
 private void exportSection(Section sec )
 {
  
  cstr.nextRow();
  cstr.addCell(sec.getType());
  
  String acc = sec.getAccNo();
  
  if( sec.getSections() != null && sec.getSections().size() > 0 && ( acc == null || acc.trim().length() == 0 ) )
   acc = "$$$"+(counter++);
  
  if( acc != null )
   cstr.addCell(acc);
  else
   cstr.nextCell();
  
  exportNodeTags(sec);

  exportAnnotation(sec);

  exportFileRefs(sec.getFileRefs());

  exportLinks(sec.getFileRefs());
  
 }

 
 private void exportFileRefs( List<FileRef> frefs )
 {
  if( frefs == null )
   return;
  
  List<FileRef> tbl = null;
  
  for( FileRef fr : frefs )
  {
   if( fr.getTableIndex() > 0 )
   {
    if( tbl == null )
     tbl=new ArrayList<FileRef>();
    
    tbl.add(fr);
   }
   else if( fr.getTableIndex() == 0 )
   {
    if( tbl != null )
    {
     exportFileTable(tbl);
     
     tbl.clear();
    }
    else
     tbl=new ArrayList<FileRef>();

    tbl.add(fr);
   }
   else
   {
    if( tbl != null )
    {
     exportFileTable(tbl);
     
     tbl=null;
    }
    
    exportFileRef( fr );
   }
  }
  
  if( tbl != null )
   exportFileTable(tbl);
  
 }
 
 private void exportFileRef(FileRef fr)
 {
  // TODO Auto-generated method stub
  
 }

 private void exportFileTable(List<FileRef> tbl)
 {
  // TODO Auto-generated method stub
  
 }
 
 
 private void exportLinks( List<Link> links )
 {
  if( links == null )
   return;
  
  List<Link> tbl = null;
  
  for( Link ln : links )
  {
   if( ln.getTableIndex() > 0 )
   {
    if( tbl == null )
     tbl=new ArrayList<Link>();
    
    tbl.add(ln);
   }
   else if( ln.getTableIndex() == 0 )
   {
    if( tbl != null )
    {
     exportLinkTable(tbl);
     
     tbl.clear();
    }
    else
     tbl=new ArrayList<Link>();

    tbl.add(ln);
   }
   else
   {
    if( tbl != null )
    {
     exportLinkTable(tbl);
     
     tbl=null;
    }
    
    exportLink( ln );
   }
  }
  
  if( tbl != null )
   exportLinkTable(tbl);
  
 }

 

 private void exportLink(Link ln)
 {
  // TODO Auto-generated method stub
  
 }

 private void exportLinkTable(List<Link> tbl)
 {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void separator(Appendable out) throws IOException
 {
  cstr.nextRow();
 }

 @Override
 public void comment(String comment, Appendable out) throws IOException
 {
  cstr.addCell(PageTabElements.CommentPrefix+comment);
 }

}
