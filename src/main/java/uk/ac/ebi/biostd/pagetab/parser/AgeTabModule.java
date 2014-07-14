package uk.ac.ebi.biostd.pagetab.parser;

import java.util.Collection;

public interface AgeTabModule
{
 AgeTabObject getObject(String part, BlockHeader classColumnHeader);

 AgeTabObject getOrCreateObject(String part, BlockHeader classColumnHeader, int ln);

 void addBlock( BlockHeader blkHdr );
 Collection<BlockHeader> getBlocks();

 Collection<AgeTabObject> getObjects(BlockHeader hdr);

 AgeTabObject createObject(String part, BlockHeader header, int ln);
 
 SyntaxProfile getSyntaxProfile();

}
