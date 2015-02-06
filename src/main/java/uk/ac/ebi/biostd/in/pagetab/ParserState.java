package uk.ac.ebi.biostd.in.pagetab;

import java.util.regex.Matcher;

public class ParserState
{
 private PageTabSyntaxParser parser;
 
 private Matcher   nameQualifierMatcher;
 private Matcher   valueQualifierMatcher;
 private Matcher   referenceMatcher;
 private Matcher   generatedAccNoMatcher;



 public void setParser(PageTabSyntaxParser parser)
 {
  this.parser = parser;
 }
 

 
 public Matcher getNameQualifierMatcher()
 {
  return nameQualifierMatcher;
 }

 public void setNameQualifierMatcher(Matcher nameQualifierMatcher)
 {
  this.nameQualifierMatcher = nameQualifierMatcher;
 }

 public Matcher getValueQualifierMatcher()
 {
  return valueQualifierMatcher;
 }

 public void setValueQualifierMatcher(Matcher valueQualifierMatcher)
 {
  this.valueQualifierMatcher = valueQualifierMatcher;
 }

 public Matcher getReferenceMatcher()
 {
  return referenceMatcher;
 }

 public void setReferenceMatcher(Matcher referenceMatcher)
 {
  this.referenceMatcher = referenceMatcher;
 }
 
 public Matcher getGeneratedAccNoMatcher()
 {
  return generatedAccNoMatcher;
 }

 public void setGeneratedAccNoMatcher(Matcher generatedAccNoMatcher)
 {
  this.generatedAccNoMatcher = generatedAccNoMatcher;
 }

 public PageTabSyntaxParser getParser()
 {
  return parser;
 }
}
