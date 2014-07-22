package uk.ac.ebi.biostd.authz;

public interface AuthzSubject
{
 boolean isUserCompatible( User u );
}
