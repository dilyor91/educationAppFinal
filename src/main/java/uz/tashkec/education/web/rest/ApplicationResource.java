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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.tashkec.education.repository.ApplicationRepository;
import uz.tashkec.education.service.ApplicationService;
import uz.tashkec.education.service.dto.ApplicationDTO;
import uz.tashkec.education.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.tashkec.education.domain.Application}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    private static final String ENTITY_NAME = "application";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationService applicationService;

    private final ApplicationRepository applicationRepository;

    public ApplicationResource(ApplicationService applicationService, ApplicationRepository applicationRepository) {
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
    }

    /**
     * {@code POST  /applications} : Create a new application.
     *
     * @param applicationDTO the applicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationDTO, or with status {@code 400 (Bad Request)} if the application has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications")
    public ResponseEntity<ApplicationDTO> createApplication(@Valid @RequestBody ApplicationDTO applicationDTO) throws URISyntaxException {
        log.debug("REST request to save Application : {}", applicationDTO);
        if (applicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new application cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationDTO result = applicationService.save(applicationDTO);
        return ResponseEntity
            .created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications/:id} : Updates an existing application.
     *
     * @param id the id of the applicationDTO to save.
     * @param applicationDTO the applicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationDTO,
     * or with status {@code 400 (Bad Request)} if the applicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApplicationDTO applicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Application : {}, {}", id, applicationDTO);
        if (applicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationDTO result = applicationService.update(applicationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applications/:id} : Partial updates given fields of an existing application, field will ignore if it is null
     *
     * @param id the id of the applicationDTO to save.
     * @param applicationDTO the applicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationDTO,
     * or with status {@code 400 (Bad Request)} if the applicationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationDTO> partialUpdateApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApplicationDTO applicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Application partially : {}, {}", id, applicationDTO);
        if (applicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationDTO> result = applicationService.partialUpdate(applicationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /applications} : get all the applications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Applications");
        Page<ApplicationDTO> page = applicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applications/:id} : get the "id" application.
     *
     * @param id the id of the applicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    /**
     * {@code DELETE  /applications/:id} : delete the "id" application.
     *
     * @param id the id of the applicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
