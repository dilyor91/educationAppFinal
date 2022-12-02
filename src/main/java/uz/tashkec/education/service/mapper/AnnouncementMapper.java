package uz.tashkec.education.service.mapper;

import org.mapstruct.*;
import uz.tashkec.education.domain.Announcement;
import uz.tashkec.education.service.dto.AnnouncementDTO;

/**
 * Mapper for the entity {@link Announcement} and its DTO {@link AnnouncementDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnnouncementMapper extends EntityMapper<AnnouncementDTO, Announcement> {}
