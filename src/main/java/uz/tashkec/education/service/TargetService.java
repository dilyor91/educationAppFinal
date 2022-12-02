package uz.tashkec.education.service;

import java.util.List;
import java.util.Optional;
import uz.tashkec.education.service.dto.TargetDTO;

/**
 * Service Interface for managing {@link uz.tashkec.education.domain.Target}.
 */
public interface TargetService {
    /**
     * Save a target.
     *
     * @param targetDTO the entity to save.
     * @return the persisted entity.
     */
    TargetDTO save(TargetDTO targetDTO);

    /**
     * Updates a target.
     *
     * @param targetDTO the entity to update.
     * @return the persisted entity.
     */
    TargetDTO update(TargetDTO targetDTO);

    /**
     * Partially updates a target.
     *
     * @param targetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TargetDTO> partialUpdate(TargetDTO targetDTO);

    /**
     * Get all the targets.
     *
     * @return the list of entities.
     */
    List<TargetDTO> findAll();

    /**
     * Get the "id" target.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TargetDTO> findOne(Long id);

    /**
     * Delete the "id" target.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
