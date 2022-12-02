package uz.tashkec.education.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uz.tashkec.education.domain.Groups} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GroupsDTO implements Serializable {

    private Long id;

    @NotNull
    private String groupNo;

    @NotNull
    private Instant startHour;

    @NotNull
    private Instant endHour;

    @NotNull
    private Integer capacity;

    @NotNull
    private Integer reservedPlace;

    @NotNull
    private Boolean status;

    private Boolean full;

    private UserDTO user;

    private PeriodDTO period;

    private CourseDTO course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public Instant getStartHour() {
        return startHour;
    }

    public void setStartHour(Instant startHour) {
        this.startHour = startHour;
    }

    public Instant getEndHour() {
        return endHour;
    }

    public void setEndHour(Instant endHour) {
        this.endHour = endHour;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getReservedPlace() {
        return reservedPlace;
    }

    public void setReservedPlace(Integer reservedPlace) {
        this.reservedPlace = reservedPlace;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(PeriodDTO period) {
        this.period = period;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupsDTO)) {
            return false;
        }

        GroupsDTO groupsDTO = (GroupsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, groupsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupsDTO{" +
            "id=" + getId() +
            ", groupNo='" + getGroupNo() + "'" +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", capacity=" + getCapacity() +
            ", reservedPlace=" + getReservedPlace() +
            ", status='" + getStatus() + "'" +
            ", full='" + getFull() + "'" +
            ", user=" + getUser() +
            ", period=" + getPeriod() +
            ", course=" + getCourse() +
            "}";
    }
}
