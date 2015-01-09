package uk.ac.ebi.biostd.mngxxx;



public interface ServiceManager
{
 UserManager getUserManager();

 SessionManager getSessionManager();

 String getServiceName();

 SubmissionManager getSubmissionManager();


}
