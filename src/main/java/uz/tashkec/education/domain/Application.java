package uz.tashkec.education.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.tashkec.education.domain.enumeration.AppStatusEnum;
import uz.tashkec.education.domain.enumeration.GenderEnum;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private Instant birthday;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;

    @NotNull
    @Column(name = "passport_no", nullable = false)
    private String passportNo;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "occupation", nullable = false)
    private String occupation;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "mob_phone", nullable = false)
    private String mobPhone;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "notification_method")
    private String notificationMethod;

    @Column(name = "certificate_no")
    private String certificateNo;

    @Column(name = "certificate_given_date")
    private Instant certificateGivenDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppStatusEnum status;

    @ManyToOne
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "period", "course" }, allowSetters = true)
    private Groups groups;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Application id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Application firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Application lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Application middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Instant getBirthday() {
        return this.birthday;
    }

    public Application birthday(Instant birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public GenderEnum getGender() {
        return this.gender;
    }

    public Application gender(GenderEnum gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getPassportNo() {
        return this.passportNo;
    }

    public Application passportNo(String passportNo) {
        this.setPassportNo(passportNo);
        return this;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Application nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public Application occupation(String occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return this.address;
    }

    public Application address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobPhone() {
        return this.mobPhone;
    }

    public Application mobPhone(String mobPhone) {
        this.setMobPhone(mobPhone);
        return this;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public Application homePhone(String homePhone) {
        this.setHomePhone(homePhone);
        return this;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getNotificationMethod() {
        return this.notificationMethod;
    }

    public Application notificationMethod(String notificationMethod) {
        this.setNotificationMethod(notificationMethod);
        return this;
    }

    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    public String getCertificateNo() {
        return this.certificateNo;
    }

    public Application certificateNo(String certificateNo) {
        this.setCertificateNo(certificateNo);
        return this;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Instant getCertificateGivenDate() {
        return this.certificateGivenDate;
    }

    public Application certificateGivenDate(Instant certificateGivenDate) {
        this.setCertificateGivenDate(certificateGivenDate);
        return this;
    }

    public void setCertificateGivenDate(Instant certificateGivenDate) {
        this.certificateGivenDate = certificateGivenDate;
    }

    public AppStatusEnum getStatus() {
        return this.status;
    }

    public Application status(AppStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AppStatusEnum status) {
        this.status = status;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Application course(Course course) {
        this.setCourse(course);
        return this;
    }

    public Groups getGroups() {
        return this.groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public Application groups(Groups groups) {
        this.setGroups(groups);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", gender='" + getGender() + "'" +
            ", passportNo='" + getPassportNo() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobPhone='" + getMobPhone() + "'" +
            ", homePhone='" + getHomePhone() + "'" +
            ", notificationMethod='" + getNotificationMethod() + "'" +
            ", certificateNo='" + getCertificateNo() + "'" +
            ", certificateGivenDate='" + getCertificateGivenDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
