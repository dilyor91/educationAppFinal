package uz.tashkec.education.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uz.tashkec.education.domain.Course} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourseDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameUz;

    @NotNull
    private String nameRu;

    @NotNull
    private String subNameUz;

    @NotNull
    private String subNameRu;

    @NotNull
    private Boolean firstCourse;

    @NotNull
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUz() {
        return nameUz;
    }

    public void setNameUz(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getSubNameUz() {
        return subNameUz;
    }

    public void setSubNameUz(String subNameUz) {
        this.subNameUz = subNameUz;
    }

    public String getSubNameRu() {
        return subNameRu;
    }

    public void setSubNameRu(String subNameRu) {
        this.subNameRu = subNameRu;
    }

    public Boolean getFirstCourse() {
        return firstCourse;
    }

    public void setFirstCourse(Boolean firstCourse) {
        this.firstCourse = firstCourse;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDTO)) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseDTO{" +
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
