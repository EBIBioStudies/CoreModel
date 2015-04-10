package uk.ac.ebi.biostd.splitstr;

import java.util.List;

import uk.ac.ebi.biostd.util.StringUtils;

public class SplitStrTest
{

 public static void main(String[] args)
 {
  
  String s="a=b";
  show(s,0);

  s="a=b=c";
  show(s,0);

  s="a=b=c=";
  show(s,0);

  s="=a=b=c=";
  show(s,0);

  s="=a==b=c=";
  show(s,0);
  
  s="=a=\\=b=c=";
  show(s,0);

  s="=a=\\=b\\\\=c=";
  show(s,0);
 
  s="=a=\\=b\\\\\\=c=";
  show(s,0);
  
  s="=a=\\=b\\\\\\=c=";
  show(s,2);

  s="a=b";
  show(s,2);
 
  s="a=b=c";
  show(s,2);

  s="a\\=b=c";
  show(s,2);

  s="a=b";
  show(s,1);

 }
 
 static void show( String s, int n )
 {
  List<String> spl = StringUtils.splitEscapedString(s, '=', '\\', n);
  
  System.out.print("String: '"+s+"' split: "+spl+" cleaned: ");
  
  for( int i=0; i < spl.size(); i++ )
   spl.set(i, StringUtils.removeEscapes(spl.get(i), '\\') );
  
  System.out.println(spl);
 }

}
