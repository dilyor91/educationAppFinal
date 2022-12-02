package uz.tashkec.education.service.mapper;

import org.mapstruct.*;
import uz.tashkec.education.domain.Target;
import uz.tashkec.education.service.dto.TargetDTO;

/**
 * Mapper for the entity {@link Target} and its DTO {@link TargetDTO}.
 */
@Mapper(componentModel = "spring")
public interface TargetMapper extends EntityMapper<TargetDTO, Target> {}
