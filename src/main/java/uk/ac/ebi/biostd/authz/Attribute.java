package uk.ac.ebi.biostd.authz;

//import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * Created by andrew on 23/03/2017.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = Attribute.GetByNameQuery, query = "SELECT t FROM Attribute t where t.name=:" + Attribute.AttributeNameQueryParameter),
        @NamedQuery(name = Attribute.GetAllQuery, query = "SELECT t FROM Attribute t ")
})
@Table(
        indexes = {
                @Index(name = "name_idx", columnList = "name", unique = true)
        })

public class Attribute {
    public static final String GetByNameQuery = "Attribute.getByName";
    public static final String GetAllQuery = "Attribute.getAll";
    public static final String AttributeNameQueryParameter = "name";

    private long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
