package uz.tashkec.education.service.mapper;

import org.mapstruct.*;
import uz.tashkec.education.domain.Participiant;
import uz.tashkec.education.service.dto.ParticipiantDTO;

/**
 * Mapper for the entity {@link Participiant} and its DTO {@link ParticipiantDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParticipiantMapper extends EntityMapper<ParticipiantDTO, Participiant> {}
