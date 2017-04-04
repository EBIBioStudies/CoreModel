package uk.ac.ebi.biostd.authz;

import javax.persistence.*;

/**
 * Created by andrew on 23/03/2017.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = TextSubscription.GetAllByAttributeQuery,
                query = "SELECT ts FROM TextSubscription ts LEFT JOIN ts.user u "
                + "where ts.attribute in (:" + TextSubscription.AttributeQueryParameter + ")"),


        @NamedQuery(name = TextSubscription.GetUsersByAttributeQuery,
                query = "SELECT DISTINCT u FROM TextSubscription ts LEFT JOIN ts.user u where ts.attribute in (:" +
                        TextSubscription.AttributeQueryParameter + ")"),

        @NamedQuery(name = TextSubscription.GetByAttributeAndUserIdQuery,
                query = "SELECT ts FROM TextSubscription ts where ts.attribute in (:" +
                        TextSubscription.AttributeQueryParameter + ") order by ts.user.id"),

        @NamedQuery(name = TextSubscription.GetAllByUserIdQuery,
                query = "SELECT ts FROM TextSubscription ts LEFT JOIN ts.user u "
                + "where  u.id=:" + TextSubscription.UserIdQueryParameter)

})
@Table(
indexes = {@Index(name = "attribute_index",  columnList="attribute", unique = false)})
public class TextSubscription {

    public static final String GetAllByAttributeQuery = "TextSubscription.getAllByAttributeQuery";

    public static final String GetUsersByAttributeQuery = "TextSubscription.getUsersByAttribute";
    public static final String GetByAttributeAndUserIdQuery = "TextSubscription.getByAttributeIdAndUser";
    public static final String GetAllByUserIdQuery = "TextSubscription.getAllByUserIdQuery";

    public static final String UserIdQueryParameter = "userId";
    public static final String AttributeQueryParameter = "attribute";


    private long id;
    private User user;
    private String attribute;
    private String pattern;

    public TextSubscription() {
    }

    public String getAttribute() { return attribute; }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }

    @ManyToOne
    //@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_fk"))
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
