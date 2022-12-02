package uz.tashkec.education.service.mapper;

import org.mapstruct.*;
import uz.tashkec.education.domain.Task;
import uz.tashkec.education.service.dto.TaskDTO;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {}
