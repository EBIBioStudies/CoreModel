package uk.ac.ebi.biostd.db;

public interface DB extends TagResolver
{
 void startTransaction();
 
 void commit();
 void rollback();
}
