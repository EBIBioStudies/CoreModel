package uk.ac.ebi.biostd.authz;

public interface GroupACR extends ACR
{

 @Override
 UserGroup getSubject();
 void setSubject( UserGroup u );
 
}
