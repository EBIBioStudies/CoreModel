package uk.ac.ebi.biostd.authz;

import uk.ac.ebi.biostd.model.Submission;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * Created by andrew on 24/03/2017.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = SubscriptionMatchEvent.GetAllUsersWithEventsQuery,
                query="SELECT DISTINCT u FROM SubscriptionMatchEvent se LEFT JOIN se.user u"),

        @NamedQuery(name = SubscriptionMatchEvent.GetEventsByUserIdQuery,
                query = "SELECT se FROM SubscriptionMatchEvent se LEFT JOIN se.user u " +
                        "LEFT JOIN se.subscription ts LEFT JOIN se.submission subm " +
                        "where u.id=:" + SubscriptionMatchEvent.UserIdQueryParameter),

        @NamedQuery(name = SubscriptionMatchEvent.DeleteEventsByUserIdQuery,
                query="delete from SubscriptionMatchEvent se " +
                        "where se.user.id=:" + SubscriptionMatchEvent.UserIdQueryParameter)
})
public class SubscriptionMatchEvent {

    public static final String GetAllUsersWithEventsQuery = "SubscriptionMatchEvent.getAllUsersWithEventsQuery";
    public static final String GetEventsByUserIdQuery = "SubscriptionMatchEvent.getEventsByUserIdQuery";
    public static final String DeleteEventsByUserIdQuery = "SubscriptionMatchEvent.deleteEventsByUserIdQuery";

    public static final String UserIdQueryParameter = "userId";


    private long id;
    private User user;
    private TextSubscription subscription;
    private Submission submission;

    public SubscriptionMatchEvent() {
    }

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    public TextSubscription getSubscription() { return subscription; }
    public void setSubscription(TextSubscription subscription) {
        this.subscription = subscription;
    }

    @ManyToOne
    @JoinColumn(name = "submission_id")
    public Submission getSubmission() { return submission; }
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

}
