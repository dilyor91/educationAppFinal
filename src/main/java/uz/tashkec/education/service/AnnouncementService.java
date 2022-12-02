package uz.tashkec.education.service;

import java.util.List;
import java.util.Optional;
import uz.tashkec.education.service.dto.AnnouncementDTO;

/**
 * Service Interface for managing {@link uz.tashkec.education.domain.Announcement}.
 */
public interface AnnouncementService {
    /**
     * Save a announcement.
     *
     * @param announcementDTO the entity to save.
     * @return the persisted entity.
     */
    AnnouncementDTO save(AnnouncementDTO announcementDTO);

    /**
     * Updates a announcement.
     *
     * @param announcementDTO the entity to update.
     * @return the persisted entity.
     */
    AnnouncementDTO update(AnnouncementDTO announcementDTO);

    /**
     * Partially updates a announcement.
     *
     * @param announcementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnnouncementDTO> partialUpdate(AnnouncementDTO announcementDTO);

    /**
     * Get all the announcements.
     *
     * @return the list of entities.
     */
    List<AnnouncementDTO> findAll();

    /**
     * Get the "id" announcement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnnouncementDTO> findOne(Long id);

    /**
     * Delete the "id" announcement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
