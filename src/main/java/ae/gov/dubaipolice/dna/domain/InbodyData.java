package ae.gov.dubaipolice.dna.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A InbodyData.
 */
@Entity
@Table(name = "inbody_data")
public class InbodyData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "column_value")
    private String columnValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InbodyData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserID() {
        return this.userID;
    }

    public InbodyData userID(Integer userID) {
        this.setUserID(userID);
        return this;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public InbodyData columnName(String columnName) {
        this.setColumnName(columnName);
        return this;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return this.columnValue;
    }

    public InbodyData columnValue(String columnValue) {
        this.setColumnValue(columnValue);
        return this;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InbodyData)) {
            return false;
        }
        return id != null && id.equals(((InbodyData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InbodyData{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            ", columnName='" + getColumnName() + "'" +
            ", columnValue='" + getColumnValue() + "'" +
            "}";
    }
}
