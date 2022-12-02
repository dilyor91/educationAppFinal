package uz.tashkec.education.service;

import java.util.List;
import java.util.Optional;
import uz.tashkec.education.service.dto.ParticipiantDTO;

/**
 * Service Interface for managing {@link uz.tashkec.education.domain.Participiant}.
 */
public interface ParticipiantService {
    /**
     * Save a participiant.
     *
     * @param participiantDTO the entity to save.
     * @return the persisted entity.
     */
    ParticipiantDTO save(ParticipiantDTO participiantDTO);

    /**
     * Updates a participiant.
     *
     * @param participiantDTO the entity to update.
     * @return the persisted entity.
     */
    ParticipiantDTO update(ParticipiantDTO participiantDTO);

    /**
     * Partially updates a participiant.
     *
     * @param participiantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParticipiantDTO> partialUpdate(ParticipiantDTO participiantDTO);

    /**
     * Get all the participiants.
     *
     * @return the list of entities.
     */
    List<ParticipiantDTO> findAll();

    /**
     * Get the "id" participiant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParticipiantDTO> findOne(Long id);

    /**
     * Delete the "id" participiant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
