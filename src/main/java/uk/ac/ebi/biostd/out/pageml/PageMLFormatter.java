package uk.ac.ebi.biostd.out.pageml;

import static uk.ac.ebi.biostd.in.pageml.PageMLAttributes.ACCESS;
import static uk.ac.ebi.biostd.in.pageml.PageMLAttributes.CLASS;
import static uk.ac.ebi.biostd.in.pageml.PageMLAttributes.ID;
import static uk.ac.ebi.biostd.in.pageml.PageMLAttributes.TYPE;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.ATTRIBUTE;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.ATTRIBUTES;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.FILE;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.FILES;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.HEADER;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.LINK;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.LINKS;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.ROOT;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.SECTION;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.SUBMISSION;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.SUBMISSIONS;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.SUBSECTIONS;
import static uk.ac.ebi.biostd.in.pageml.PageMLElements.TABLE;
import static uk.ac.ebi.biostd.util.StringUtils.xmlEscaped;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.in.PMDoc;
import uk.ac.ebi.biostd.in.pageml.PageMLElements;
import uk.ac.ebi.biostd.in.pagetab.SubmissionInfo;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Annotated;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.out.DocumentFormatter;
import uk.ac.ebi.biostd.out.TextStreamFormatter;

public class PageMLFormatter implements TextStreamFormatter, DocumentFormatter
{
 protected static final String shiftSym = " "; 

 protected String initShift = shiftSym+shiftSym;

 private Appendable outStream;
 
 public PageMLFormatter()
 {}
 
 public PageMLFormatter( Appendable o )
 {
  outStream = o;
 }

 
 @Override
 public void format(PMDoc document) throws IOException 
 {
  header(document.getHeaders(), outStream);
  
  for( SubmissionInfo s : document.getSubmissions() )
   format(s.getSubmission(), outStream);
  
  footer(outStream);
 }
 

 @Override
 public void header(Map<String, List<String>> hdrs, Appendable out) throws IOException
 {
  out.append("<").append(ROOT.getElementName()).append(">\n");
  
  if( hdrs != null )
  {
   for( Map.Entry<String, List<String>> me : hdrs.entrySet() )
   {
    for( String val : me.getValue() )
    {
     out.append(shiftSym).append("<").append(HEADER.getElementName()).append(">\n");

     out.append(shiftSym).append(shiftSym).append("<").append(PageMLElements.NAME.getElementName()).append(">");
     xmlEscaped(me.getKey(), out);
     out.append("</").append(PageMLElements.NAME.getElementName()).append(">\n");

     out.append(shiftSym).append(shiftSym).append("<").append(PageMLElements.VALUE.getElementName()).append(">");
     xmlEscaped(val, out);
     out.append("</").append(PageMLElements.VALUE.getElementName()).append(">\n");

     out.append(shiftSym).append("</").append(HEADER.getElementName()).append(">\n");

    }
   }
  }
  
  out.append(shiftSym).append("<").append(SUBMISSIONS.getElementName()).append(">\n");

 }

 @Override
 public void footer(Appendable out) throws IOException
 {
  out.append(shiftSym).append("</").append(SUBMISSIONS.getElementName()).append(">\n");
  out.append("</").append(ROOT.getElementName()).append(">\n");
 }

 @Override
 public void separator(Appendable out) throws IOException
 {
 }

 @Override
 public void comment(String comment, Appendable out) throws IOException
 {
  out.append("<!-- ");
  xmlEscaped(comment);
  out.append(" -->\n");
 }
 

 @Override
 public void format(Submission s, Appendable out) throws IOException
 {
  formatSubmission( s, out, initShift );
 }

 protected void formatSubmission(Submission subm, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append('<').append(SUBMISSION.getElementName()).append(' ').append(ID.getAttrName()).append("=\"");
  xmlEscaped(subm.getAccNo(), out);
  
  String str = subm.getEntityClass();
  if( str != null && str.length() > 0 )
  {
   out.append("\" ").append(CLASS.getAttrName()).append("=\"");
   xmlEscaped(str,out);
  }
  
  if( subm.getAccessTags() != null && subm.getAccessTags().size() > 0 )
  {
   out.append("\" ").append(ACCESS.getAttrName()).append("=\"");
   
   boolean first = true;
   for( AccessTag at : subm.getAccessTags() )
   {
    if( first )
     first = false;
    else
     out.append(';');
    
    xmlEscaped(at.getName(),out);
   }
   
  }

  
  out.append("\">\n\n");

  String contShift = shift+shiftSym;

  formatAttributes(subm, out, contShift);

  out.append("\n");
  

  if( subm.getRootSection() != null )
   formatSection(subm.getRootSection(), out, contShift);
  
  out.append("\n");
  out.append(shift);
  out.append("</").append(SUBMISSION.getElementName()).append(">\n");
 }

 protected void formatSection(Section sec, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append('<').append(SECTION.getElementName()).append(' ').append(TYPE.getAttrName()).append("=\"");
  xmlEscaped(sec.getType(), out);
  
  if( sec.getAccNo() != null )
  {
   out.append("\" ").append(ID.getAttrName()).append("=\"");
   xmlEscaped(sec.getAccNo(),out);
  }
  
  String str = sec.getEntityClass();
  if( str != null && str.length() > 0 )
  {
   out.append("\" ").append(CLASS.getAttrName()).append("=\"");
   xmlEscaped(str,out);
  }
  
  if( sec.getAccessTags() != null && sec.getAccessTags().size() > 0 )
  {
   out.append("\" ").append(ACCESS.getAttrName()).append("=\"");
   
   boolean first = true;
   for( AccessTag at : sec.getAccessTags() )
   {
    if( first )
     first = false;
    else
     out.append(',');
    
    xmlEscaped(at.getName(),out);
   }
   
  }
  
  out.append("\">\n\n");

  String contShift = shift+shiftSym;

  formatAttributes(sec, out, contShift);

  if( sec.getFileRefs() != null && sec.getFileRefs().size() > 0 )
  {
   formatFileRefs(sec, out, contShift);
   out.append("\n");
  }

  if( sec.getLinks() != null && sec.getLinks().size() > 0 )
  {
   formatLinks(sec, out, contShift);
   out.append("\n");
  }


  if( sec.getSections() != null && sec.getSections().size() > 0 )
   formatSubsections(sec.getSections(), out, contShift);
  
  out.append("\n");
  out.append(shift);
  out.append("</").append(SECTION.getElementName()).append(">\n\n");

 }

 private void formatSubsections(List<Section> lst, Appendable out, String shift ) throws IOException
 {
  String contShift = shift+shiftSym;
  
  out.append(shift);
  out.append("<").append(SUBSECTIONS.getElementName()).append(">\n");
 
  int lastTblIdx = -1;
  
  boolean hasTable=false;
  
  for( Section ssec : lst )
  {
   if( ssec.getTableIndex() <= lastTblIdx && hasTable )
   {
    contShift = shift+shiftSym;

    out.append(contShift);
    out.append("</").append(TABLE.getElementName()).append(">\n");
    
    hasTable = false;
   }   
   
   if( ssec.getTableIndex() >=0 )
   {
    if( ! hasTable )
    {
     out.append(contShift);
     out.append("<").append(TABLE.getElementName()).append(">\n");

     contShift = contShift + shiftSym;
     hasTable = true;
    }
   }

   lastTblIdx=ssec.getTableIndex();
   
   formatSection(ssec, out, contShift);
  }
  
  
  if( hasTable )
  {
   out.append(shift+shiftSym);
   out.append("</").append(TABLE.getElementName()).append(">\n");
  }
  
  out.append(shift);
  out.append("</").append(SUBSECTIONS.getElementName()).append(">\n");
 }
 
 private void formatLinks(Section s, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append("<").append(LINKS.getElementName()).append(">\n");
  
  String contShift = shift+shiftSym;

  
  
  int lastTblIdx = -1;
  
  boolean hasTable=false;
  
  for( Link ln : s.getLinks() )
  {
   if( ln.getTableIndex() <= lastTblIdx && hasTable )
   {
    contShift = shift+shiftSym;

    out.append(contShift);
    out.append("</").append(TABLE.getElementName()).append(">\n");
    
    hasTable = false;
   }
   
   if( ln.getTableIndex() >=0 )
   {
    if( ! hasTable )
    {
     out.append(contShift);
     out.append("<").append(TABLE.getElementName()).append(">\n");

     contShift = contShift + shiftSym;
     hasTable = true;
    }
   }
   
   lastTblIdx=ln.getTableIndex();
   
   out.append(contShift);
   
   out.append('<').append(LINK.getElementName());

//   out.append('<').append(LINK.getElementName()).append(' ').append(URL.getAttrName()).append("=\"");
//   xmlEscaped(ln.getUrl(),out);
   
   String str = ln.getEntityClass();
   if( str != null && str.length() > 0 )
   {
    out.append(" ").append(CLASS.getAttrName()).append("=\"");
    xmlEscaped(str,out);
    out.append("\"");
   }
   
   if( ln.getAccessTags() != null && ln.getAccessTags().size() > 0 )
   {
    out.append(" ").append(ACCESS.getAttrName()).append("=\"");
    
    boolean first = true;
    for( AccessTag at : ln.getAccessTags() )
    {
     if( first )
      first = false;
     else
      out.append(';');
     
     xmlEscaped(at.getName(),out);
    }
    
    out.append("\"");

   }
   
   out.append(">\n");

   out.append(contShift+shiftSym).append('<').append(PageMLElements.URL.getElementName()).append(">");
   xmlEscaped(ln.getUrl(),out);
   out.append("</").append(PageMLElements.URL.getElementName()).append(">\n");
   
    
   formatAttributes(ln, out, contShift+shiftSym);

   out.append(contShift);
   out.append("</").append(LINK.getElementName()).append(">\n");

  }
  
  if( hasTable )
  {
   out.append(shift+shiftSym);
   out.append("</").append(TABLE.getElementName()).append(">\n");
  }
  
  out.append(shift);
  out.append("</").append(LINKS.getElementName()).append(">\n");

 }

 private void formatFileRefs(Section s, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append("<").append(FILES.getElementName()).append(">\n");
  
  String contShift = shift+shiftSym;

  int lastTblIdx = -1;
  
  boolean hasTable=false;
  
  for( FileRef fr : s.getFileRefs() )
  {
   if( fr.getTableIndex() <= lastTblIdx && hasTable )
   {
    contShift = shift+shiftSym;

    out.append(contShift);
    out.append("</").append(TABLE.getElementName()).append(">\n");
    
    hasTable = false;
   }   
   
   if( fr.getTableIndex() >=0 )
   {
    if( ! hasTable )
    {
     out.append(contShift);
     out.append("<").append(TABLE.getElementName()).append(">\n");

     contShift = contShift + shiftSym;
     hasTable = true;
    }
   }

   lastTblIdx=fr.getTableIndex();
   
   out.append(contShift);
   
   out.append('<').append(FILE.getElementName());
   
   String str = fr.getEntityClass();
   if( str != null && str.length() > 0 )
   {
    out.append(" ").append(CLASS.getAttrName()).append("=\"");
    xmlEscaped(str,out);
    out.append("\"");
   }
   
   if( fr.getAccessTags() != null && fr.getAccessTags().size() > 0 )
   {
    out.append(" ").append(ACCESS.getAttrName()).append("=\"");
    
    boolean first = true;
    for( AccessTag at : fr.getAccessTags() )
    {
     if( first )
      first = false;
     else
      out.append(';');
     
     xmlEscaped(at.getName(),out);
    }

    out.append("\"");

   }

   out.append(">\n");

   out.append(contShift+shiftSym).append('<').append(PageMLElements.PATH.getElementName()).append(">");
   xmlEscaped(fr.getName(),out);
   out.append("</").append(PageMLElements.PATH.getElementName()).append(">\n");
   
    
   formatAttributes(fr, out, contShift+shiftSym);

   out.append(contShift);
   out.append("</").append(FILE.getElementName()).append(">\n");

  }
  
  if( hasTable )
  {
   out.append(shift+shiftSym);
   out.append("</").append(TABLE.getElementName()).append(">\n");
  }

  
  out.append(shift);
  out.append("</").append(FILES.getElementName()).append(">\n");
 }

 protected void formatAttributes(Annotated ent, Appendable out, String shift ) throws IOException
 {
  out.append(shift);
  out.append("<").append(ATTRIBUTES.getElementName());
  
  String atShift = shift+shiftSym;
  
  List<? extends AbstractAttribute> attrs = ent.getAttributes();
  
  if( attrs == null || attrs.size() == 0 )
  {
   out.append("/>\n");
   return;
  }
  else
   out.append(">\n");

  for( AbstractAttribute at : attrs )
  {
   out.append(atShift);
   out.append('<').append(ATTRIBUTE.getElementName());

   if( at.isReference() )
    out.append(" reference=\"true\"");
   
   
   String str = at.getEntityClass();
   if( str != null && str.length() > 0 )
   {
    out.append(" ").append(CLASS.getAttrName()).append("=\"");
    xmlEscaped(str,out);
    out.append("\"");
   }
   
   out.append(">\n");
   
   String vshift = atShift+shiftSym;
   
   out.append(vshift).append('<').append(PageMLElements.NAME.getElementName()).append('>');
   xmlEscaped(at.getName(),out);
   out.append("</").append(PageMLElements.NAME.getElementName()).append(">\n");

   
   List<Qualifier> qlist = at.getNameQualifiers();
   
   if( qlist != null && qlist.size() > 0 )
   {
    for( Qualifier q : qlist )
     formatQualifier(q, PageMLElements.NMQUALIFIER.getElementName(), out, vshift);
   }
 
   out.append(vshift).append('<').append(PageMLElements.VALUE.getElementName()).append('>');
   xmlEscaped(at.getValue(),out);
   out.append("</").append(PageMLElements.VALUE.getElementName()).append(">\n");

   
   qlist = at.getValueQualifiers();
   
   if( qlist != null && qlist.size() > 0 )
   {
    for( Qualifier q : qlist )
     formatQualifier(q, PageMLElements.VALQUALIFIER.getElementName(), out, vshift);
   }

   
   out.append(atShift);
   out.append("</").append(ATTRIBUTE.getElementName()).append(">\n");

  }

  out.append(shift);
  out.append("</").append(ATTRIBUTES.getElementName()).append(">\n");
  
 }
 
 private void formatQualifier(Qualifier q, String xmltag, Appendable out, String shift ) throws IOException
 {
  out.append(shift).append('<').append(xmltag).append(">\n");
  
  String vshift = shift+shiftSym;
  
  out.append(vshift).append('<').append(PageMLElements.NAME.getElementName()).append('>');
  xmlEscaped(q.getName(),out);
  out.append("</").append(PageMLElements.NAME.getElementName()).append(">\n");
  
  out.append(vshift).append('<').append(PageMLElements.VALUE.getElementName()).append('>');
  xmlEscaped(q.getValue(),out);
  out.append("</").append(PageMLElements.VALUE.getElementName()).append(">\n");

  out.append(shift).append("</").append(xmltag).append(">\n");
 }




}
