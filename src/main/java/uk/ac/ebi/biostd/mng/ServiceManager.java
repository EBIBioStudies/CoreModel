package uk.ac.ebi.biostd.mng;



public interface ServiceManager
{
 UserManager getUserManager();

 SessionManager getSessionManager();

 String getServiceName();

 SubmissionManager getSubmissionManager();

}
