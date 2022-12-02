package uz.tashkec.education.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name_uz", nullable = false)
    private String nameUz;

    @NotNull
    @Column(name = "name_ru", nullable = false)
    private String nameRu;

    @NotNull
    @Column(name = "sub_name_uz", nullable = false)
    private String subNameUz;

    @NotNull
    @Column(name = "sub_name_ru", nullable = false)
    private String subNameRu;

    @NotNull
    @Column(name = "first_course", nullable = false)
    private Boolean firstCourse;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUz() {
        return this.nameUz;
    }

    public Course nameUz(String nameUz) {
        this.setNameUz(nameUz);
        return this;
    }

    public void setNameUz(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameRu() {
        return this.nameRu;
    }

    public Course nameRu(String nameRu) {
        this.setNameRu(nameRu);
        return this;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getSubNameUz() {
        return this.subNameUz;
    }

    public Course subNameUz(String subNameUz) {
        this.setSubNameUz(subNameUz);
        return this;
    }

    public void setSubNameUz(String subNameUz) {
        this.subNameUz = subNameUz;
    }

    public String getSubNameRu() {
        return this.subNameRu;
    }

    public Course subNameRu(String subNameRu) {
        this.setSubNameRu(subNameRu);
        return this;
    }

    public void setSubNameRu(String subNameRu) {
        this.subNameRu = subNameRu;
    }

    public Boolean getFirstCourse() {
        return this.firstCourse;
    }

    public Course firstCourse(Boolean firstCourse) {
        this.setFirstCourse(firstCourse);
        return this;
    }

    public void setFirstCourse(Boolean firstCourse) {
        this.firstCourse = firstCourse;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Course status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", nameUz='" + getNameUz() + "'" +
            ", nameRu='" + getNameRu() + "'" +
            ", subNameUz='" + getSubNameUz() + "'" +
            ", subNameRu='" + getSubNameRu() + "'" +
            ", firstCourse='" + getFirstCourse() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
