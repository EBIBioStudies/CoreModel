package uk.ac.ebi.biostd.export;

import static uk.ac.ebi.biostd.pageml.PageMLAttributes.ACCESS;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.CLASS;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.ID;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.NAME;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.NAMEQ;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.TYPE;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.URL;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.VALUEQ;
import static uk.ac.ebi.biostd.pageml.PageMLElements.ATTRIBUTE;
import static uk.ac.ebi.biostd.pageml.PageMLElements.ATTRIBUTES;
import static uk.ac.ebi.biostd.pageml.PageMLElements.FILE;
import static uk.ac.ebi.biostd.pageml.PageMLElements.FILES;
import static uk.ac.ebi.biostd.pageml.PageMLElements.LINK;
import static uk.ac.ebi.biostd.pageml.PageMLElements.LINKS;
import static uk.ac.ebi.biostd.pageml.PageMLElements.SECTION;
import static uk.ac.ebi.biostd.pageml.PageMLElements.SUBSECTIONS;
import static uk.ac.ebi.biostd.util.StringUtils.xmlEscaped;

import java.io.IOException;
import java.util.List;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Annotated;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;

public class PageMLFormatter implements Formatter
{
 protected static final String shiftSym = " "; 

 protected String initShift = shiftSym;
 
 @Override
 public void format(Submission s, Appendable out) throws IOException
 {
  formatSection(s.getRootSection(),out, initShift );
 }

 protected void formatSection(Section sec, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append('<').append(SECTION.getElementName()).append(' ').append(TYPE.getAttrName()).append("=\"");
  xmlEscaped(sec.getType(), out);
  
  if( sec.getAcc() != null )
  {
   out.append("\" ").append(ID.getAttrName()).append("=\"");
   xmlEscaped(sec.getAcc(),out);
  }
  
  if( sec.getEntityClass() != null )
  {
   out.append("\" ").append(CLASS.getAttrName()).append("=\"");
   xmlEscaped(sec.getEntityClass(),out);
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
     out.append(';');
    
    xmlEscaped(at.getName());
   }
   
  }
  
  out.append("\">\n\n");

  String contShift = shift+shiftSym;

  formatAttributes(sec, out, contShift);

  out.append("\n");
  
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
 
  for( Section ssec : lst )
   formatSection(ssec, out, contShift);

  out.append(shift);
  out.append("</").append(SUBSECTIONS.getElementName()).append(">\n");
 }
 
 private void formatLinks(Section s, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append("<").append(LINKS.getElementName()).append(">\n");
  
  String contShift = shift+shiftSym;

  for( Link ln : s.getLinks() )
  {
   out.append(contShift);
   
   out.append('<').append(LINK.getElementName()).append(' ').append(URL.getAttrName()).append("=\"");
   xmlEscaped(ln.getUrl());
   
   if( ln.getEntityClass() != null && ln.getEntityClass().length() > 0 )
   {
    out.append("\" ").append(CLASS.getAttrName()).append("=\"");
    xmlEscaped(ln.getEntityClass());
   }
   
   if( ln.getAccessTags() != null && ln.getAccessTags().size() > 0 )
   {
    out.append("\" ").append(ACCESS.getAttrName()).append("=\"");
    
    boolean first = true;
    for( AccessTag at : ln.getAccessTags() )
    {
     if( first )
      first = false;
     else
      out.append(';');
     
     xmlEscaped(at.getName());
    }
   }
   
   if( ln.getAttributes() == null && ln.getAttributes().size() == 0 )
    out.append("\"/>\n");
   else
   {
    out.append("\">\n");
    
    formatAttributes(ln, out, contShift+shiftSym);

    out.append(contShift);
    out.append("</").append(LINK.getElementName()).append(">\n");
   }

  }
  
  out.append(shift);
  out.append("</").append(LINKS.getElementName()).append(">\n");

 }

 private void formatFileRefs(Section s, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append("<").append(FILES.getElementName()).append(">\n");
  
  String contShift = shift+shiftSym;

  for( FileRef fr : s.getFileRefs() )
  {
   out.append(contShift);
   
   out.append('<').append(FILE.getElementName()).append(' ').append(NAME.getAttrName()).append("=\"");
   xmlEscaped(fr.getName());
   
   if( fr.getEntityClass() != null && fr.getEntityClass().length() > 0 )
   {
    out.append("\" ").append(CLASS.getAttrName()).append("=\"");
    xmlEscaped(fr.getEntityClass());
   }
   
   if( fr.getAccessTags() != null && fr.getAccessTags().length() > 0 )
   {
    out.append("\" ").append(ACCESS.getAttrName()).append("=\"");
    xmlEscaped(fr.getAccessTags());
   }

   if( fr.getAttributes() == null && fr.getAttributes().size() == 0 )
    out.append("\"/>\n");
   else
   {
    out.append("\">\n");
    
    formatAttributes(fr, out, contShift+shiftSym);

    out.append(contShift);
    out.append("</").append(FILE.getElementName()).append(">\n");
   }

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

  for( AbstractAttribute at : attrs )
  {
   out.append(atShift);
   out.append('<').append(ATTRIBUTE.getElementName()).append(' ').append(NAME.getAttrName()).append("=\"");
   xmlEscaped(at.getName());

//   out.append("\" value=\"");
//   xmlEscaped(at.getValue());
   
   if( at.getEntityClass() != null && at.getEntityClass().length() > 0 )
   {
    out.append("\" ").append(CLASS.getAttrName()).append("=\"");
    xmlEscaped(at.getEntityClass());
   }
   
   if( at.getNameQualifier() != null && at.getNameQualifier().length() > 0 )
   {
    out.append("\" ").append(NAMEQ.getAttrName()).append("=\"");
    xmlEscaped(at.getNameQualifier());
   }

   if( at.getValueQualifier() != null && at.getValueQualifier().length() > 0 )
   {
    out.append("\" ").append(VALUEQ.getAttrName()).append("=\"");
    xmlEscaped(at.getValueQualifier());
   }
   
   out.append("\">");
   xmlEscaped(at.getValue());
   out.append("</").append(ATTRIBUTE.getElementName()).append(">\n");


  }

  out.append(shift);
  out.append("</").append(ATTRIBUTES.getElementName()).append(">\n");
  
 }
}
