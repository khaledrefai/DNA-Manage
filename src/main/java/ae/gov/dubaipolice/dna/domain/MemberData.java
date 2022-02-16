package ae.gov.dubaipolice.dna.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A MemberData.
 */
@Entity
@Table(name = "member_data")
public class MemberData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "test_date")
    private Instant testDate;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_gender")
    private String userGender;

    @Column(name = "user_birthday")
    private String userBirthday;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_height")
    private Float userHeight;

    @Column(name = "order_date")
    private Instant orderDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTestDate() {
        return this.testDate;
    }

    public MemberData testDate(Instant testDate) {
        this.setTestDate(testDate);
        return this;
    }

    public void setTestDate(Instant testDate) {
        this.testDate = testDate;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public MemberData userId(Integer userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public MemberData userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return this.userGender;
    }

    public MemberData userGender(String userGender) {
        this.setUserGender(userGender);
        return this;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserBirthday() {
        return this.userBirthday;
    }

    public MemberData userBirthday(String userBirthday) {
        this.setUserBirthday(userBirthday);
        return this;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public Integer getUserAge() {
        return this.userAge;
    }

    public MemberData userAge(Integer userAge) {
        this.setUserAge(userAge);
        return this;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Float getUserHeight() {
        return this.userHeight;
    }

    public MemberData userHeight(Float userHeight) {
        this.setUserHeight(userHeight);
        return this;
    }

    public void setUserHeight(Float userHeight) {
        this.userHeight = userHeight;
    }

    public Instant getOrderDate() {
        return this.orderDate;
    }

    public MemberData orderDate(Instant orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberData)) {
            return false;
        }
        return id != null && id.equals(((MemberData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberData{" +
            "id=" + getId() +
            ", testDate='" + getTestDate() + "'" +
            ", userId=" + getUserId() +
            ", userName='" + getUserName() + "'" +
            ", userGender='" + getUserGender() + "'" +
            ", userBirthday='" + getUserBirthday() + "'" +
            ", userAge=" + getUserAge() +
            ", userHeight=" + getUserHeight() +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }
}
