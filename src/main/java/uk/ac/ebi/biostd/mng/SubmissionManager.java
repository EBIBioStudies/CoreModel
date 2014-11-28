package uk.ac.ebi.biostd.mng;

import java.util.Collection;

import uk.ac.ebi.biostd.authz.User;
import uk.ac.ebi.biostd.model.Submission;
import uk.ac.ebi.biostd.treelog.LogNode;

public interface SubmissionManager
{
 Collection< Submission > getSubmissionsByOwner( User u);

 LogNode createSubmission(String txt);
}
