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
import uz.tashkec.education.repository.ParticipiantRepository;
import uz.tashkec.education.service.ParticipiantService;
import uz.tashkec.education.service.dto.ParticipiantDTO;
import uz.tashkec.education.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.tashkec.education.domain.Participiant}.
 */
@RestController
@RequestMapping("/api")
public class ParticipiantResource {

    private final Logger log = LoggerFactory.getLogger(ParticipiantResource.class);

    private static final String ENTITY_NAME = "participiant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipiantService participiantService;

    private final ParticipiantRepository participiantRepository;

    public ParticipiantResource(ParticipiantService participiantService, ParticipiantRepository participiantRepository) {
        this.participiantService = participiantService;
        this.participiantRepository = participiantRepository;
    }

    /**
     * {@code POST  /participiants} : Create a new participiant.
     *
     * @param participiantDTO the participiantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participiantDTO, or with status {@code 400 (Bad Request)} if the participiant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participiants")
    public ResponseEntity<ParticipiantDTO> createParticipiant(@Valid @RequestBody ParticipiantDTO participiantDTO)
        throws URISyntaxException {
        log.debug("REST request to save Participiant : {}", participiantDTO);
        if (participiantDTO.getId() != null) {
            throw new BadRequestAlertException("A new participiant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParticipiantDTO result = participiantService.save(participiantDTO);
        return ResponseEntity
            .created(new URI("/api/participiants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /participiants/:id} : Updates an existing participiant.
     *
     * @param id the id of the participiantDTO to save.
     * @param participiantDTO the participiantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participiantDTO,
     * or with status {@code 400 (Bad Request)} if the participiantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participiantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participiants/{id}")
    public ResponseEntity<ParticipiantDTO> updateParticipiant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParticipiantDTO participiantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Participiant : {}, {}", id, participiantDTO);
        if (participiantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participiantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participiantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParticipiantDTO result = participiantService.update(participiantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participiantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /participiants/:id} : Partial updates given fields of an existing participiant, field will ignore if it is null
     *
     * @param id the id of the participiantDTO to save.
     * @param participiantDTO the participiantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participiantDTO,
     * or with status {@code 400 (Bad Request)} if the participiantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the participiantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the participiantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/participiants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParticipiantDTO> partialUpdateParticipiant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParticipiantDTO participiantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Participiant partially : {}, {}", id, participiantDTO);
        if (participiantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participiantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participiantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParticipiantDTO> result = participiantService.partialUpdate(participiantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participiantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /participiants} : get all the participiants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participiants in body.
     */
    @GetMapping("/participiants")
    public List<ParticipiantDTO> getAllParticipiants() {
        log.debug("REST request to get all Participiants");
        return participiantService.findAll();
    }

    /**
     * {@code GET  /participiants/:id} : get the "id" participiant.
     *
     * @param id the id of the participiantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participiantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participiants/{id}")
    public ResponseEntity<ParticipiantDTO> getParticipiant(@PathVariable Long id) {
        log.debug("REST request to get Participiant : {}", id);
        Optional<ParticipiantDTO> participiantDTO = participiantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participiantDTO);
    }

    /**
     * {@code DELETE  /participiants/:id} : delete the "id" participiant.
     *
     * @param id the id of the participiantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participiants/{id}")
    public ResponseEntity<Void> deleteParticipiant(@PathVariable Long id) {
        log.debug("REST request to delete Participiant : {}", id);
        participiantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
