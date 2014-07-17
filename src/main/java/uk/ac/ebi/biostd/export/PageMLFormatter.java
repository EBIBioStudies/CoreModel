package uk.ac.ebi.biostd.export;

import static uk.ac.ebi.biostd.util.StringUtils.xmlEscaped;

import java.io.IOException;

import uk.ac.ebi.biostd.model.Annotated;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;

public class PageMLFormatter implements Formatter
{

 @Override
 public void format(Submission s, Appendable out) throws IOException
 {
  formatSection(s.getRootSection(),out);
 }

 protected void formatSection(Section sec, Appendable out) throws IOException
 {
  out.append("<section type=\"");
  xmlEscaped(sec.getType(), out);
  out.append('"');
  
  if( sec.getAcc() != null )
  {
   out.append(" id=\"");
   xmlEscaped(sec.getAcc(),out);
   out.append('"');
  }
  
  if( sec.getEntityClass() != null )
  {
   out.append(" class=\"");
   xmlEscaped(sec.getEntityClass(),out);
   out.append('"');
  }
  
  out.append(">\n");


  formatAttributes(sec, out);
 }

 protected void formatAttributes(Annotated ent, Appendable out ) throws IOException
 {
  out.append("<attributes");

 }
}
