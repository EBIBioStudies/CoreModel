package uk.ac.ebi.biostd.export;

import static uk.ac.ebi.biostd.pageml.PageMLAttributes.ACCESS;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.CLASS;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.ID;
import static uk.ac.ebi.biostd.pageml.PageMLElements.SUBMISSION;
import static uk.ac.ebi.biostd.util.StringUtils.xmlEscaped;

import java.io.IOException;

import uk.ac.ebi.biostd.authz.AccessTag;
import uk.ac.ebi.biostd.model.Submission;

public class SubmissionPageMLFormatter extends PageMLFormatter
{

 @Override
 public void format(Submission s, Appendable out) throws IOException
 {
  formatSubmission(s, out, initShift );
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

}
