package sn.sonatel.dsi.ins.imoc.web.rest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.sonatel.dsi.ins.imoc.domain.Trajet;
import sn.sonatel.dsi.ins.imoc.repository.TrajetRepository;
import sn.sonatel.dsi.ins.imoc.security.SecurityUtils;
import sn.sonatel.dsi.ins.imoc.service.TrajetQueryService;
import sn.sonatel.dsi.ins.imoc.service.TrajetService;
import sn.sonatel.dsi.ins.imoc.service.criteria.TrajetCriteria;
import sn.sonatel.dsi.ins.imoc.service.dto.TrajetDTO;
import sn.sonatel.dsi.ins.imoc.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.sonatel.dsi.ins.imoc.domain.Trajet}.
 */
@RestController
@RequestMapping("/api/trajets")
public class TrajetResource {

    private final Logger log = LoggerFactory.getLogger(TrajetResource.class);

    private static final String ENTITY_NAME = "bookyoonTrajetserviceTrajet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrajetService trajetService;

    private final TrajetRepository trajetRepository;

    private final TrajetQueryService trajetQueryService;

    public TrajetResource(TrajetService trajetService, TrajetRepository trajetRepository, TrajetQueryService trajetQueryService) {
        this.trajetService = trajetService;
        this.trajetRepository = trajetRepository;
        this.trajetQueryService = trajetQueryService;
    }

    /**
     * {@code POST  /trajets} : Create a new trajet.
     *
     * @param trajetDTO the trajetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trajetDTO, or with status {@code 400 (Bad Request)} if the trajet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<TrajetDTO> createTrajet(@Valid @RequestBody TrajetDTO trajetDTO) throws URISyntaxException {
        log.debug("REST request to save Trajet : {}", trajetDTO);

        //Verifie le role
        if (!SecurityUtils.hasCurrentUserThisAuthority("ROLE_CONDUCTEUR")) {
            throw new AccessDeniedException("Only Conductors can create a trip");
        }

        if (trajetDTO.getId() != null) {
            throw new BadRequestAlertException("A new trajet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trajetDTO = trajetService.save(trajetDTO);
        return ResponseEntity.created(new URI("/api/trajets/" + trajetDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, trajetDTO.getId().toString()))
            .body(trajetDTO);
    }

    /**
     * {@code PUT  /trajets/:id} : Updates an existing trajet.
     *
     * @param id the id of the trajetDTO to save.
     * @param trajetDTO the trajetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajetDTO,
     * or with status {@code 400 (Bad Request)} if the trajetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trajetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<TrajetDTO> updateTrajet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrajetDTO trajetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Trajet : {}, {}", id, trajetDTO);
        if (trajetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trajetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trajetDTO = trajetService.update(trajetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajetDTO.getId().toString()))
            .body(trajetDTO);
    }

    /**
     * {@code PATCH  /trajets/:id} : Partial updates given fields of an existing trajet, field will ignore if it is null
     *
     * @param id the id of the trajetDTO to save.
     * @param trajetDTO the trajetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajetDTO,
     * or with status {@code 400 (Bad Request)} if the trajetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trajetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trajetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<TrajetDTO> partialUpdateTrajet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrajetDTO trajetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trajet partially : {}, {}", id, trajetDTO);
//        if (trajetDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        if (!Objects.equals(id, trajetDTO.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//        }
//
//        if (!trajetRepository.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }

        trajetDTO.setId(id);
        Optional<TrajetDTO> result = trajetService.partialUpdate(trajetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajetDTO.getId().toString())
        );
    }


    /**
     * {@code GET  /trajets} : get all the trajets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trajets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TrajetDTO>> getAllTrajets(
        TrajetCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Trajets by criteria: {}", criteria);

        Page<TrajetDTO> page = trajetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/mes-trajets")
    public ResponseEntity<List<TrajetDTO>> getAllMyTrajets(TrajetCriteria criteria) {
        log.debug("REST request to get my Trajets by criteria: {}", criteria);

        List<TrajetDTO> list = trajetQueryService.findMyTrajectsByCriteria(criteria);
        return ResponseEntity.ok(list);
    }

    /**
     * {@code GET  /trajets/count} : count all the trajets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTrajets(TrajetCriteria criteria) {
        log.debug("REST request to count Trajets by criteria: {}", criteria);
        return ResponseEntity.ok().body(trajetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trajets/:id} : get the "id" trajet.
     *
     * @param id the id of the trajetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trajetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrajetDTO> getTrajet(@PathVariable("id") Long id) {
        log.debug("REST request to get Trajet : {}", id);
        Optional<TrajetDTO> trajetDTO = trajetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trajetDTO);
    }

    /**
     * {@code DELETE  /trajets/:id} : delete the "id" trajet.
     *
     * @param id the id of the trajetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable("id") Long id) {
        log.debug("REST request to delete Trajet : {}", id);
        trajetService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }


    // Récupére l'historique des trajets
    @GetMapping("/history")
    public ResponseEntity<List<TrajetDTO>> getTrajetsHistory() {
        List<TrajetDTO> trajets = trajetService.getTrajetsHistory();
        return ResponseEntity.ok(trajets);
    }
    @GetMapping("/mes-trajets-a-venir")
    public ResponseEntity<List<TrajetDTO>> getTrajetsMesAvenirEtActifs() {
        List<TrajetDTO> trajets = trajetService.getTrajetsMesAvenirEtActifs();
        return ResponseEntity.ok(trajets);
    }

    //Récupere le nombre de place disponible
    @GetMapping("/{trajetId}/places-disponibles")
    public ResponseEntity<Integer> getPlacesDisponibles(@PathVariable Long trajetId) {
        Integer placesDisponibles = trajetService.placesDisponibles(trajetId);
        return ResponseEntity.ok(placesDisponibles);
    }

    @GetMapping("/{trajetId}/users")
    public ResponseEntity<List<String>> getUsersForTrajet(@PathVariable("trajetId") Long trajetid) {
        List<String> userLogins = trajetService.getUserLoginsForTrajet(trajetid);
        return ResponseEntity.ok(userLogins);
    }

    @GetMapping("/{trajetId}/nombre-annulations")
    public ResponseEntity<Integer> getAnnulation(@PathVariable Long trajetId) {
        Integer annulations = trajetService.getAnnulation(trajetId);
        return ResponseEntity.ok(annulations);
    }
    @GetMapping("/{trajetId}/places-restantes")
    public  ResponseEntity<Integer> getPlacesRestantes(@PathVariable Long trajetId){
        Integer placesRestantes = trajetService.getPlacesRestantes(trajetId);
        return ResponseEntity.ok(placesRestantes);
    }

    @GetMapping("/a-venir")
    public ResponseEntity<List<Trajet>> getTrajetsAvenirEtActifs() {
        List<Trajet> trajets = trajetService.getTrajetsAvenirEtActifs();
        return ResponseEntity.ok(trajets);
    }



    @PutMapping("/{id}/annuler")
    public ResponseEntity<Void> annulerTrajet(@PathVariable("id") Long trajetId) {
        trajetService.annulerTrajet(trajetId);
        return ResponseEntity.noContent().build();
    }


}
