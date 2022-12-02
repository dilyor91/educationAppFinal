package uz.tashkec.education.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.tashkec.education.repository.TargetRepository;
import uz.tashkec.education.service.TargetService;
import uz.tashkec.education.service.dto.TargetDTO;
import uz.tashkec.education.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.tashkec.education.domain.Target}.
 */
@RestController
@RequestMapping("/api")
public class TargetResource {

    private final Logger log = LoggerFactory.getLogger(TargetResource.class);

    private static final String ENTITY_NAME = "target";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TargetService targetService;

    private final TargetRepository targetRepository;

    public TargetResource(TargetService targetService, TargetRepository targetRepository) {
        this.targetService = targetService;
        this.targetRepository = targetRepository;
    }

    /**
     * {@code POST  /targets} : Create a new target.
     *
     * @param targetDTO the targetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new targetDTO, or with status {@code 400 (Bad Request)} if the target has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/targets")
    public ResponseEntity<TargetDTO> createTarget(@Valid @RequestBody TargetDTO targetDTO) throws URISyntaxException {
        log.debug("REST request to save Target : {}", targetDTO);
        if (targetDTO.getId() != null) {
            throw new BadRequestAlertException("A new target cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TargetDTO result = targetService.save(targetDTO);
        return ResponseEntity
            .created(new URI("/api/targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /targets/:id} : Updates an existing target.
     *
     * @param id the id of the targetDTO to save.
     * @param targetDTO the targetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetDTO,
     * or with status {@code 400 (Bad Request)} if the targetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the targetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/targets/{id}")
    public ResponseEntity<TargetDTO> updateTarget(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TargetDTO targetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Target : {}, {}", id, targetDTO);
        if (targetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!targetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TargetDTO result = targetService.update(targetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, targetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /targets/:id} : Partial updates given fields of an existing target, field will ignore if it is null
     *
     * @param id the id of the targetDTO to save.
     * @param targetDTO the targetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetDTO,
     * or with status {@code 400 (Bad Request)} if the targetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the targetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the targetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/targets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TargetDTO> partialUpdateTarget(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TargetDTO targetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Target partially : {}, {}", id, targetDTO);
        if (targetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!targetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TargetDTO> result = targetService.partialUpdate(targetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, targetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /targets} : get all the targets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of targets in body.
     */
    @GetMapping("/targets")
    public List<TargetDTO> getAllTargets() {
        log.debug("REST request to get all Targets");
        return targetService.findAll();
    }

    /**
     * {@code GET  /targets/:id} : get the "id" target.
     *
     * @param id the id of the targetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the targetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/targets/{id}")
    public ResponseEntity<TargetDTO> getTarget(@PathVariable Long id) {
        log.debug("REST request to get Target : {}", id);
        Optional<TargetDTO> targetDTO = targetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(targetDTO);
    }

    /**
     * {@code DELETE  /targets/:id} : delete the "id" target.
     *
     * @param id the id of the targetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/targets/{id}")
    public ResponseEntity<Void> deleteTarget(@PathVariable Long id) {
        log.debug("REST request to delete Target : {}", id);
        targetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
