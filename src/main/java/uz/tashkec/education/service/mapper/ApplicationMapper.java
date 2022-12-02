package uz.tashkec.education.service.mapper;

import org.mapstruct.*;
import uz.tashkec.education.domain.Application;
import uz.tashkec.education.domain.Course;
import uz.tashkec.education.domain.Groups;
import uz.tashkec.education.service.dto.ApplicationDTO;
import uz.tashkec.education.service.dto.CourseDTO;
import uz.tashkec.education.service.dto.GroupsDTO;

/**
 * Mapper for the entity {@link Application} and its DTO {@link ApplicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationMapper extends EntityMapper<ApplicationDTO, Application> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    @Mapping(target = "groups", source = "groups", qualifiedByName = "groupsId")
    ApplicationDTO toDto(Application s);

    @Named("courseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseDTO toDtoCourseId(Course course);

    @Named("groupsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupsDTO toDtoGroupsId(Groups groups);
}
