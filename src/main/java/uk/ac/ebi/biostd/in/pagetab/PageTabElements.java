package uk.ac.ebi.biostd.in.pagetab;

public class PageTabElements
{
 public static final String TagSeparator1        = ";";
 public static final String TagSeparator2        = ",";

 public static final char NameQOpen        = '(';
 public static final char NameQClose        = ')';
 public static final char ValueQOpen        = '[';
 public static final char ValueQClose        = ']';
 public static final char RefOpen        = '<';
 public static final char RefClose        = '>';
 public static final char TableOpen        = '[';
 public static final char TableClose        = ']';

 
 public static final String TagSeparatorRX        = "["+TagSeparator1+TagSeparator2+"]";
 public static final String ClassifierSeparatorRX = ":";
 public static final String ValueTagSeparatorRX   = "=";
 public static final String CommentPrefix   = "#";
 public static final String EscCommentPrefix   = "\\#";
 public static final String DocParamPrefix   = "#@";

 public static final String NameQualifierRx  = "\\s*\\"+NameQOpen+"\\s*(?<name>[^\\)]+)\\s*\\"+NameQClose+"\\s*";
 public static final String ValueQualifierRx = "\\s*\\"+ValueQOpen+"\\s*(?<name>[^\\]]+)\\s*\\"+ValueQOpen+"\\s*";
 public static final String ReferenceRx = "\\s*\\"+RefOpen+"\\s*(?<name>[^\\>]+)\\s*\\"+RefClose+"\\s*";
 public static final String TableBlockRx     = "\\s*(?<name>[^\\s\\[]+)\\"+TableOpen+"\\s*(?<parent>[^\\]\\s]+)?\\s*\\"+TableClose+"\\s*";

 public static final String SubmissionKeyword     = "Submission";
 public static final String FileKeyword           = "File";
 public static final String LinkKeyword           = "Link";
 public static final String LinkTableKeyword      = "Links";
 public static final String FileTableKeyword      = "Files";
}
