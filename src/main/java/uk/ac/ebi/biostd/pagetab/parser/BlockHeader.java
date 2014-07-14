package uk.ac.ebi.biostd.pagetab.parser;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.biostd.model.Section;

public class BlockHeader
{
 private boolean horizontal;
 private ClassReference classCol;
 private final List<ClassReference> props=new ArrayList<ClassReference>(30);
 private final Section section;
 
 public BlockHeader( Section s )
 {
  section = s;
 }
 
 public Section getModule()
 {
  return section;
 }
 
 /* (non-Javadoc)
  * @see uk.ac.ebi.age.parser.impl.BlockHeader#setClassColumnHeader(uk.ac.ebi.age.parser.ColumnHeader)
  */
 public void setClassColumnHeader(ClassReference cc)
 {
  classCol=cc;
 }

 /* (non-Javadoc)
  * @see uk.ac.ebi.age.parser.impl.BlockHeader#addColumnHeader(uk.ac.ebi.age.parser.ColumnHeader)
  */
 public void addColumnHeader(ClassReference chd)
 {
  props.add(chd);
 }

 /* (non-Javadoc)
  * @see uk.ac.ebi.age.parser.impl.BlockHeader#getClassColumnHeader()
  */
 public ClassReference getClassColumnHeader()
 {
  return classCol;
 }

 /* (non-Javadoc)
  * @see uk.ac.ebi.age.parser.impl.BlockHeader#getColumnHeaders()
  */
 public List<ClassReference> getColumnHeaders()
 {
  return props;
 }

 public boolean isHorizontal()
 {
  return horizontal;
 }

 public void setHorizontal(boolean horizontal)
 {
  this.horizontal = horizontal;
 }
}
