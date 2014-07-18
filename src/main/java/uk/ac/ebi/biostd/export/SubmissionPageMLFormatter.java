package uk.ac.ebi.biostd.export;

import static uk.ac.ebi.biostd.pageml.PageMLAttributes.ACCESS;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.CLASS;
import static uk.ac.ebi.biostd.pageml.PageMLAttributes.ID;
import static uk.ac.ebi.biostd.pageml.PageMLElements.SUBMISSION;
import static uk.ac.ebi.biostd.util.StringUtils.xmlEscaped;

import java.io.IOException;

import uk.ac.ebi.biostd.model.Submission;

public class SubmissionPageMLFormatter extends PageMLFormatter
{

 @Override
 public void format(Submission s, Appendable out) throws IOException
 {
  formatSubmission(s, out, initShift );
 }

 protected void formatSubmission(Submission sec, Appendable out, String shift) throws IOException
 {
  out.append(shift);
  out.append('<').append(SUBMISSION.getElementName()).append(' ').append(ID.getAttrName()).append("=\"");
  xmlEscaped(sec.getAcc(), out);
  
  if( sec.getEntityClass() != null )
  {
   out.append("\" ").append(CLASS.getAttrName()).append("=\"");
   xmlEscaped(sec.getEntityClass(),out);
  }
  
  if( sec.getAccessTags() != null && sec.getAccessTags().length() > 0 )
  {
   out.append("\" ").append(ACCESS.getAttrName()).append("=\"");
   xmlEscaped(sec.getAccessTags());
  }

  
  out.append("\">\n\n");

  String contShift = shift+shiftSym;

  formatAttributes(sec, out, contShift);

  out.append("\n");
  

  if( sec.getRootSection() != null )
   formatSection(sec.getRootSection(), out, contShift);
  
  out.append("\n");
  out.append(shift);
  out.append("</").append(SUBMISSION.getElementName()).append(">\n");
 }

}
