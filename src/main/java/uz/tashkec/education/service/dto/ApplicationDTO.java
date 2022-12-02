package uz.tashkec.education.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;
import uz.tashkec.education.domain.enumeration.AppStatusEnum;
import uz.tashkec.education.domain.enumeration.GenderEnum;

/**
 * A DTO for the {@link uz.tashkec.education.domain.Application} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String middleName;

    @NotNull
    private Instant birthday;

    @NotNull
    private GenderEnum gender;

    @NotNull
    private String passportNo;

    @NotNull
    private String nationality;

    @NotNull
    private String occupation;

    @NotNull
    private String address;

    @NotNull
    private String mobPhone;

    private String homePhone;

    private String notificationMethod;

    private String certificateNo;

    private Instant certificateGivenDate;

    private AppStatusEnum status;

    private CourseDTO course;

    private GroupsDTO groups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getNotificationMethod() {
        return notificationMethod;
    }

    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Instant getCertificateGivenDate() {
        return certificateGivenDate;
    }

    public void setCertificateGivenDate(Instant certificateGivenDate) {
        this.certificateGivenDate = certificateGivenDate;
    }

    public AppStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AppStatusEnum status) {
        this.status = status;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public GroupsDTO getGroups() {
        return groups;
    }

    public void setGroups(GroupsDTO groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationDTO)) {
            return false;
        }

        ApplicationDTO applicationDTO = (ApplicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationDTO{" +
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
            ", course=" + getCourse() +
            ", groups=" + getGroups() +
            "}";
    }
}
