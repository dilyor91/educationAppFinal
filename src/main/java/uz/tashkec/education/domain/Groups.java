package uz.tashkec.education.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Groups.
 */
@Entity
@Table(name = "groups")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "group_no", nullable = false)
    private String groupNo;

    @NotNull
    @Column(name = "start_hour", nullable = false)
    private Instant startHour;

    @NotNull
    @Column(name = "end_hour", nullable = false)
    private Instant endHour;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Column(name = "reserved_place", nullable = false)
    private Integer reservedPlace;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "jhi_full")
    private Boolean full;

    @ManyToOne
    private User user;

    @ManyToOne
    private Period period;

    @ManyToOne
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Groups id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNo() {
        return this.groupNo;
    }

    public Groups groupNo(String groupNo) {
        this.setGroupNo(groupNo);
        return this;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public Instant getStartHour() {
        return this.startHour;
    }

    public Groups startHour(Instant startHour) {
        this.setStartHour(startHour);
        return this;
    }

    public void setStartHour(Instant startHour) {
        this.startHour = startHour;
    }

    public Instant getEndHour() {
        return this.endHour;
    }

    public Groups endHour(Instant endHour) {
        this.setEndHour(endHour);
        return this;
    }

    public void setEndHour(Instant endHour) {
        this.endHour = endHour;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Groups capacity(Integer capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getReservedPlace() {
        return this.reservedPlace;
    }

    public Groups reservedPlace(Integer reservedPlace) {
        this.setReservedPlace(reservedPlace);
        return this;
    }

    public void setReservedPlace(Integer reservedPlace) {
        this.reservedPlace = reservedPlace;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Groups status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getFull() {
        return this.full;
    }

    public Groups full(Boolean full) {
        this.setFull(full);
        return this;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Groups user(User user) {
        this.setUser(user);
        return this;
    }

    public Period getPeriod() {
        return this.period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Groups period(Period period) {
        this.setPeriod(period);
        return this;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Groups course(Course course) {
        this.setCourse(course);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Groups)) {
            return false;
        }
        return id != null && id.equals(((Groups) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Groups{" +
            "id=" + getId() +
            ", groupNo='" + getGroupNo() + "'" +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", capacity=" + getCapacity() +
            ", reservedPlace=" + getReservedPlace() +
            ", status='" + getStatus() + "'" +
            ", full='" + getFull() + "'" +
            "}";
    }
}
