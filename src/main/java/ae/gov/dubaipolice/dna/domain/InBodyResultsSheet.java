package ae.gov.dubaipolice.dna.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A InBodyResultsSheet.
 */
@Entity
@Table(name = "in_body_results_sheet")
public class InBodyResultsSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "datetimes")
    private Instant datetimes;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "inbody_image")
    private String inbodyImage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InBodyResultsSheet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public InBodyResultsSheet userId(Integer userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Instant getDatetimes() {
        return this.datetimes;
    }

    public InBodyResultsSheet datetimes(Instant datetimes) {
        this.setDatetimes(datetimes);
        return this;
    }

    public void setDatetimes(Instant datetimes) {
        this.datetimes = datetimes;
    }

    public Instant getOrderDate() {
        return this.orderDate;
    }

    public InBodyResultsSheet orderDate(Instant orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getInbodyImage() {
        return this.inbodyImage;
    }

    public InBodyResultsSheet inbodyImage(String inbodyImage) {
        this.setInbodyImage(inbodyImage);
        return this;
    }

    public void setInbodyImage(String inbodyImage) {
        this.inbodyImage = inbodyImage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InBodyResultsSheet)) {
            return false;
        }
        return id != null && id.equals(((InBodyResultsSheet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InBodyResultsSheet{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", datetimes='" + getDatetimes() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", inbodyImage='" + getInbodyImage() + "'" +
            "}";
    }
}
