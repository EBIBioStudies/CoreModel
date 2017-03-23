package uk.ac.ebi.biostd.authz;

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
 * Created by andrew on 23/03/2017.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = TextSubscription.GetUsersByAttributeIdsQuery,
                query = "SELECT DISTINCT u FROM TextSubscription ts LEFT JOIN ts.user u where ts.attribute.id in (:" +
                        TextSubscription.AttributeIdQueryParameter + ")"),

        @NamedQuery(name = TextSubscription.GetUsersByAttributeNamesQuery,
                query = "SELECT DISTINCT u FROM TextSubscription ts LEFT JOIN ts.user u LEFT JOIN ts.attribute att where att.name in (:" +
                        TextSubscription.AttributeNameQueryParameter + ")"),


        @NamedQuery(name = TextSubscription.GetByAttributeIdsAndUserIdQuery,
                query = "SELECT ts FROM TextSubscription ts where ts.attribute.id in (:" +
                        TextSubscription.AttributeIdQueryParameter + ") order by ts.user.id"),

        @NamedQuery(name = TextSubscription.GetByAttributeNamesAndUserIdQuery,
                query = "SELECT ts FROM TextSubscription ts where ts.attribute.name in (:" +
                        TextSubscription.AttributeNameQueryParameter + ") order by ts.user.id"),


        @NamedQuery(name = TextSubscription.GetAllByAttributeIdsQuery,
                query = "SELECT ts FROM TextSubscription ts LEFT JOIN ts.user u LEFT JOIN ts.attribute att "
                + "where att.id in (:" + TextSubscription.AttributeIdQueryParameter + ")"),


        @NamedQuery(name = TextSubscription.GetAllByUserIdQuery,
                query = "SELECT ts FROM TextSubscription ts LEFT JOIN ts.user u LEFT JOIN ts.attribute att "
                + "where  u.id=:" + TextSubscription.UserIdQueryParameter)

})
public class TextSubscription {

    public static final String GetUsersByAttributeIdsQuery = "TextSubscription.getUsersByAttributeIds";
    public static final String GetUsersByAttributeNamesQuery = "TextSubscription.getUsersByAttributeNames";

    public static final String GetByAttributeIdsAndUserIdQuery = "TextSubscription.getByAttributeIdAndUser";
    public static final String GetByAttributeNamesAndUserIdQuery = "TextSubscription.getByAttributeNameAndUser";

    public static final String GetAllByUserIdQuery = "TextSubscription.getAllByUserIdQuery";
    public static final String GetAllByAttributeIdsQuery = "TextSubscription.getAllByAttributeIdsQuery";


    public static final String UserIdQueryParameter = "userId";
    public static final String AttributeIdQueryParameter = "attributeId";
    public static final String AttributeNameQueryParameter = "attributeName";


    private long id;
    private User user;
    private Attribute attribute;
    private String pattern;

    public TextSubscription() {
    }

    @ManyToOne
    @JoinColumn(name = "attribute_id", foreignKey = @ForeignKey(name = "attribute_fk"))
    public Attribute getAttribute() { return attribute; }
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_fk"))
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

}
