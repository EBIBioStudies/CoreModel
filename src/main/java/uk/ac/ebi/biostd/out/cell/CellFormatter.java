package uk.ac.ebi.biostd.out.cell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.biostd.authz.AccessTag;
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
 private interface KeyExtactor
 {
  String getKey( Node n );
 }
 
 private static class AttrHdr
 {
  LinkedHashMap<String,AttrHdr> quals;
  String atName;
  int ord=0;
  
  @Override
  public int hashCode()
  {
   return atName.hashCode();
  }
  
  @Override
  public boolean equals(Object obj)
  {
   return atName.equals(((AttrHdr)obj).atName);
  }
 }
 
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
 {
  if( so.getAccessTags() == null || so.getAccessTags().size() == 0 )
  {
   cstr.nextCell();
   return;
  }
  
  if( so.getAccessTags().size() == 1 )
  {
   cstr.addCell(so.getAccessTags().iterator().next().getName());
   return;
  }
  
  StringBuilder sb = new StringBuilder();
  
  for( AccessTag acct :  so.getAccessTags() )
   sb.append(acct.getName()).append(PageTabElements.TagSeparator1);

  sb.setLength( sb.length() - PageTabElements.TagSeparator1.length() );
 }
 
 private void exportClassifTags( Classified clsf )
 {}
 
 private void exportTable(List<? extends Node> nodes, String titl, KeyExtactor kex )
 {
  Map<String,AttrHdr> hdrMap = new LinkedHashMap<>();
  
  Integer int1 = new Integer(1);
  
  for( Node n : nodes )
  {
   Map<String,Integer> atCntMap = new HashMap<String, Integer>();
   
   for(AbstractAttribute aa : n.getAttributes() )
   {
    String name = aa.getName();
    
    Integer cnt = atCntMap.get(name);
    
    if( cnt == null )
    {
     atCntMap.put(name, cnt = int1);
     
     name=name+"_1";
    }
    else
    {
     cnt = cnt.intValue()+1;
     atCntMap.put(name, cnt);
     
     name=name+"_"+cnt.intValue();
    }
    
    AttrHdr hdr = hdrMap.get(name);
    
    if( hdr == null )
    {
     hdrMap.put(name, hdr = new AttrHdr());
     hdr.atName = aa.getName();
    }
    else
     hdr.ord = cnt.intValue();
    
    if( aa.getValueQualifiers() != null )
    {
     Map<String,Integer> qCntMap = new HashMap<String, Integer>();

     if( hdr.quals == null )
      hdr.quals = new LinkedHashMap<String, CellFormatter.AttrHdr>();
     
     for( Qualifier q : aa.getValueQualifiers() )
     {
      String qname = q.getName();
      
      Integer qcnt = qCntMap.get(name);
      
      if( qcnt == null )
      {
       qCntMap.put(qname, qcnt = int1);
       
       qname=qname+"_1";
      }
      else
      {
       qcnt = qcnt.intValue()+1;
       qCntMap.put(qname, qcnt);
       
       qname=qname+"_"+qcnt.intValue();
      }
      
      AttrHdr qhdr = hdr.quals.get(qname);
      
      if( qhdr == null )
      {
       hdr.quals.put(qname, qhdr = new AttrHdr());
       qhdr.atName = q.getName();
      }
      else
       qhdr.ord = qcnt.intValue();
     }
    }
    
   }
  }
  
  cstr.nextRow();
  cstr.addCell(titl);
  
  for( AttrHdr ah : hdrMap.values() )
  {
   cstr.addCell(ah.atName);
   
   if( ah.quals != null)
   {
    for( AttrHdr qh : ah.quals.values() )
     cstr.addCell(String.valueOf(PageTabElements.ValueQOpen)+qh.atName+String.valueOf(PageTabElements.ValueQClose));
   }
  }
  
  for( Node n : nodes )
  {
   cstr.nextRow();
   cstr.addCell(kex.getKey(n));
   
   for( AttrHdr ah : hdrMap.values()  )
   {
    AbstractAttribute cattr=null;
    
    int aOrd = 0;
    for( AbstractAttribute aa : n.getAttributes() )
    {
     if( aa.getName().equals(ah.atName) )
     {
      if( aOrd == ah.ord )
      {
       cattr = aa;
       cstr.addCell(aa.getValue());
       break;
      }
      else
       aOrd++;
     }
    }
    
    if( cattr == null )
     cstr.nextCell();

    if( ah.quals != null )
    {
     for( AttrHdr qh : ah.quals.values()  )
     {
      if( cattr == null || cattr.getValueQualifiers() == null )
      {
       cstr.nextCell();
       continue;
      }

      int qOrd = 0;
      Qualifier cqual = null;
      for( Qualifier q : cattr.getValueQualifiers() )
      {
       if( q.getName().equals(qh.atName) )
       {
        if( qOrd == qh.ord )
        {
         cqual=q;
         cstr.addCell(q.getValue());
         break;
        }
        else
         qOrd++;
       }
      }
      
      if( cqual == null )
       cstr.nextCell();
     }

    }
   
   }
   
  }

  cstr.nextRow();
 }

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

  exportLinks(sec.getLinks());
  
  exportSubsections( sec );
  
 }

 
 private void exportSubsections( Section sec )
 {
  List<Section> secs = sec.getSections();
  
  if( secs == null )
   return;
  
  List<Section> tbl = null;
  
  String lastSecType = null;
  
  for( Section sc : secs )
  {
   if( tbl != null && !sc.getType().equals(lastSecType) )
   {
    exportSectionTable(tbl, sec);
    
    tbl=null;
   }
   
   lastSecType = sc.getType();
    
   if( sc.getTableIndex() > 0 )
   {
    if( tbl == null )
     tbl=new ArrayList<Section>();
    
    tbl.add(sc);
   }
   else if( sc.getTableIndex() == 0 )
   {
    if( tbl != null )
    {
     exportSectionTable(tbl, sec);
     
     tbl.clear();
    }
    else
     tbl=new ArrayList<Section>();

    tbl.add(sc);
   }
   else
   {
    if( tbl != null )
    {
     exportSectionTable(tbl, sec);
     
     tbl=null;
    }
    
    exportSection( sc );
   }
  }
  
  if( tbl != null )
   exportSectionTable(tbl, sec);
  
 }
 
 private void exportSectionTable(List<Section> tbl, Section parent)
 {
  String parentAcc = ( parent.getParentSection() == null )?"":parent.getAccNo();
  
  exportTable(tbl, 
    tbl.get(0).getType()+PageTabElements.TableOpen+parentAcc+PageTabElements.TableClose, 
    n -> { Section s = (Section)n; if(s.getAccNo() == null) return ""; return s.isGlobal()?"!"+s.getAccNo():s.getAccNo(); } );
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
  cstr.nextRow();
  
  cstr.addCell(PageTabElements.FileKeyword);
  cstr.addCell(fr.getName());
  
  exportNodeTags(fr);

  exportAnnotation(fr);
  
 }

 private void exportFileTable(List<FileRef> tbl)
 {
  exportTable(tbl, PageTabElements.FileTableKeyword, n -> ((FileRef)n).getName() );
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
  cstr.nextRow();
  
  cstr.addCell(PageTabElements.LinkKeyword);
  cstr.addCell(ln.getUrl());
  
  exportNodeTags(ln);

  exportAnnotation(ln);
 }

 private void exportLinkTable(List<Link> tbl)
 {
  exportTable(tbl, PageTabElements.LinkTableKeyword, n -> ((Link)n).getUrl() );
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
