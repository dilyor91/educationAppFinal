package uz.tashkec.education.service.mapper;

import org.mapstruct.*;
import uz.tashkec.education.domain.Course;
import uz.tashkec.education.domain.Groups;
import uz.tashkec.education.domain.Period;
import uz.tashkec.education.domain.User;
import uz.tashkec.education.service.dto.CourseDTO;
import uz.tashkec.education.service.dto.GroupsDTO;
import uz.tashkec.education.service.dto.PeriodDTO;
import uz.tashkec.education.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Groups} and its DTO {@link GroupsDTO}.
 */
@Mapper(componentModel = "spring")
public interface GroupsMapper extends EntityMapper<GroupsDTO, Groups> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "period", source = "period", qualifiedByName = "periodId")
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    GroupsDTO toDto(Groups s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("periodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PeriodDTO toDtoPeriodId(Period period);

    @Named("courseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseDTO toDtoCourseId(Course course);
}
