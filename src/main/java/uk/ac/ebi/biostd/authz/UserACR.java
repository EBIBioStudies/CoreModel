package uk.ac.ebi.biostd.authz;

public interface UserACR extends ACR
{

 @Override
 User getSubject();
 void setSubject( User u );
 
}
