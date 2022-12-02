package uz.tashkec.education.service.mapper;

import org.mapstruct.*;
import uz.tashkec.education.domain.Application;
import uz.tashkec.education.domain.Attachment;
import uz.tashkec.education.service.dto.ApplicationDTO;
import uz.tashkec.education.service.dto.AttachmentDTO;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "application", source = "application", qualifiedByName = "applicationId")
    AttachmentDTO toDto(Attachment s);

    @Named("applicationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationDTO toDtoApplicationId(Application application);
}
