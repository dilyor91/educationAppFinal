package uz.tashkec.education.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.tashkec.education.service.dto.PeriodDTO;

/**
 * Service Interface for managing {@link uz.tashkec.education.domain.Period}.
 */
public interface PeriodService {
    /**
     * Save a period.
     *
     * @param periodDTO the entity to save.
     * @return the persisted entity.
     */
    PeriodDTO save(PeriodDTO periodDTO);

    /**
     * Updates a period.
     *
     * @param periodDTO the entity to update.
     * @return the persisted entity.
     */
    PeriodDTO update(PeriodDTO periodDTO);

    /**
     * Partially updates a period.
     *
     * @param periodDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PeriodDTO> partialUpdate(PeriodDTO periodDTO);

    /**
     * Get all the periods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeriodDTO> findAll(Pageable pageable);

    /**
     * Get the "id" period.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeriodDTO> findOne(Long id);

    /**
     * Delete the "id" period.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
