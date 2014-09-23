package uk.ac.ebi.biostd.pageml;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.ebi.biostd.export.TagResolver;
import uk.ac.ebi.biostd.model.AbstractAttribute;
import uk.ac.ebi.biostd.model.Annotated;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.Qualifier;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.Submission;

public class PageMLParser extends DefaultHandler
{
 
 private final Stack<PageMLElements> context = new Stack<>();
 private final Stack<Object> contextObjStk = new Stack<>();
 
 private XMLReader xmlReader;
 
 private Submission sub;
 private final TagResolver reslv;
 
 public PageMLParser(TagResolver tr) throws ParserConfigurationException, SAXException
 {
  SAXParserFactory spf = SAXParserFactory.newInstance();
  spf.setNamespaceAware(false);
  
  SAXParser saxParser = spf.newSAXParser();

  
  XMLReader xmlReader = saxParser.getXMLReader();
  xmlReader.setContentHandler( this );
  
  reslv = tr;
 }
 
 public Submission parse( Reader src ) throws IOException, SAXException
 {
  sub = null;
  context.clear();
  
  xmlReader.parse( new InputSource(src) );
  
  return sub;
 }


 @Override
 public void startDocument() throws SAXException
 {
  context.push(PageMLElements.ROOT);
  context.push(null);
 }

 @Override
 public void endDocument() throws SAXException
 {
  contextObjStk.clear();
  context.clear();
 }


 @Override
 public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
 {
  PageMLElements ctx = context.peek();
  Object contextObj = contextObjStk.peek(); 
  
  if( PageMLElements.SUBMISSION.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.ROOT )
    throw new SAXException("Invalid context for element '"+PageMLElements.SUBMISSION.getElementName()+"' : '"+ctx.getElementName()+"'");
   
   sub = new Submission();
   sub.setAcc(atts.getValue(PageMLAttributes.ID.getAttrName()));
   
   sub.setTagRefs( reslv.getSubmissionTagRefs(atts.getValue(PageMLAttributes.CLASS.getAttrName())));
   sub.setAccessTags( reslv.getAccessTags(atts.getValue(PageMLAttributes.ACCESS.getAttrName())) );
   
   
   context.push( PageMLElements.SUBMISSION );
   contextObjStk.push( sub );
  }
  else if( PageMLElements.SUBSECTIONS.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.SECTION )
    throw new SAXException("Invalid context for element '"+PageMLElements.SUBSECTIONS.getElementName()+"' : '"+ctx.getElementName()+"'");
   
   context.push( PageMLElements.SUBSECTIONS );
   contextObjStk.push( contextObj );
  }
  else if( PageMLElements.SECTION.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.SUBMISSION && ctx != PageMLElements.SUBSECTIONS )
    throw new SAXException("Invalid context for element '"+PageMLElements.SECTION.getElementName()+"' : '"+ctx.getElementName()+"'");
   
   Section sec = new Section();
   
   if( ctx == PageMLElements.SUBMISSION )
    ( (Submission)contextObj ).setRootSection(sec);
   else
    ( (Section)contextObj ).addSection(sec);
   
   sec.setAcc(atts.getValue(PageMLAttributes.ID.getAttrName()));
   
   sec.setTagRefs( reslv.getSectionTagRefs(atts.getValue(PageMLAttributes.CLASS.getAttrName())));
   sec.setAccessTags( reslv.getAccessTags(atts.getValue(PageMLAttributes.ACCESS.getAttrName())) );
   
   context.push( PageMLElements.SECTION );
   contextObjStk.push( sec );

  }
  else if( PageMLElements.ATTRIBUTES.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.SECTION && ctx != PageMLElements.SUBMISSION && ctx != PageMLElements.FILE && ctx != PageMLElements.LINK )
    throw new SAXException("Invalid context for element '"+PageMLElements.ATTRIBUTES.getElementName()+"' : '"+ctx.getElementName()+"'");

   context.push( PageMLElements.ATTRIBUTES );
   contextObjStk.push( contextObj );
  }
  else if( PageMLElements.ATTRIBUTE.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.ATTRIBUTES  )
    throw new SAXException("Invalid context for element '"+PageMLElements.ATTRIBUTE.getElementName()+"' : '"+ctx.getElementName()+"'");

   String name = atts.getValue(PageMLAttributes.NAME.getAttrName());
   String nameQ = atts.getValue(PageMLAttributes.NAMEQ.getAttrName());
   String valueQ = atts.getValue(PageMLAttributes.VALUEQ.getAttrName());
   
   AbstractAttribute at = ((Annotated)contextObj).addAttribute(name, null);
   
   if( nameQ != null && nameQ.length() > 0 && valueQ != null && valueQ.length() > 0 )
    at.addQualifier( new Qualifier(nameQ,valueQ) );

   //TODO Need a format for multiple qualifiers;
   
   
   context.push( PageMLElements.ATTRIBUTE );
   contextObjStk.push( at );
  }
  else if( PageMLElements.FILES.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.SECTION )
    throw new SAXException("Invalid context for element '"+PageMLElements.FILES.getElementName()+"' : '"+ctx.getElementName()+"'");
   
   context.push( PageMLElements.FILES );
   contextObjStk.push( contextObj );
  }
  else if( PageMLElements.LINKS.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.SECTION )
    throw new SAXException("Invalid context for element '"+PageMLElements.LINKS.getElementName()+"' : '"+ctx.getElementName()+"'");
   
   context.push( PageMLElements.LINKS );
   contextObjStk.push( contextObj );
  }
  else if( PageMLElements.FILE.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.FILES )
    throw new SAXException("Invalid context for element '"+PageMLElements.FILE.getElementName()+"' : '"+ctx.getElementName()+"'");
   
   FileRef fr = new FileRef();
   
   fr.setName(atts.getValue(PageMLAttributes.NAME.getAttrName()));
  
   fr.setTagRefs( reslv.getFileTagRefs(atts.getValue(PageMLAttributes.CLASS.getAttrName())));
   fr.setAccessTags( reslv.getAccessTags(atts.getValue(PageMLAttributes.ACCESS.getAttrName())) );

   ((Section)contextObj).addFileRef(fr);
   
   context.push( PageMLElements.FILE );
   contextObjStk.push( fr );

  }
  else if( PageMLElements.LINK.getElementName().equals(localName) )
  {
   if( ctx != PageMLElements.LINK )
    throw new SAXException("Invalid context for element '"+PageMLElements.FILE.getElementName()+"' : '"+ctx.getElementName()+"'");
   
   Link lnk = new Link();
   
   lnk.setUrl(atts.getValue(PageMLAttributes.URL.getAttrName()));
   
   lnk.setTagRefs( reslv.getLinkTagRefs(atts.getValue(PageMLAttributes.CLASS.getAttrName())));
   lnk.setAccessTags( reslv.getAccessTags(atts.getValue(PageMLAttributes.ACCESS.getAttrName())) );

   ((Section)contextObj).addLink(lnk);
   
   context.push( PageMLElements.LINK );
   contextObjStk.push( lnk );

  }

 }

 @Override
 public void endElement(String uri, String localName, String qName) throws SAXException
 {
  context.pop();
  contextObjStk.pop();
 }

 @Override
 public void characters(char[] ch, int start, int length) throws SAXException
 {
  if( context.peek() == PageMLElements.ATTRIBUTE )
  { 
   AbstractAttribute at = (AbstractAttribute)contextObjStk.peek();
   
   if( at.getValue() != null )
    at.setValue( at.getValue()+new String(ch,start,length) );
   else
    at.setValue( new String(ch,start,length) );
  }
 }

}
