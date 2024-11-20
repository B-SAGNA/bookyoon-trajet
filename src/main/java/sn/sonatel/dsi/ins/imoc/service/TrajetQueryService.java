package sn.sonatel.dsi.ins.imoc.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.sonatel.dsi.ins.imoc.client.UserClient;
import sn.sonatel.dsi.ins.imoc.domain.*; // for static metamodels
import sn.sonatel.dsi.ins.imoc.domain.Trajet;
import sn.sonatel.dsi.ins.imoc.repository.TrajetRepository;
import sn.sonatel.dsi.ins.imoc.security.SecurityUtils;
import sn.sonatel.dsi.ins.imoc.service.criteria.TrajetCriteria;
import sn.sonatel.dsi.ins.imoc.service.dto.TrajetDTO;
import sn.sonatel.dsi.ins.imoc.service.mapper.TrajetMapper;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service for executing complex queries for {@link Trajet} entities in the database.
 * The main input is a {@link TrajetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TrajetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrajetQueryService extends QueryService<Trajet> {

    private final Logger log = LoggerFactory.getLogger(TrajetQueryService.class);

    private final TrajetRepository trajetRepository;

    private final TrajetMapper trajetMapper;

    private final UserClient userClient;

    public TrajetQueryService(TrajetRepository trajetRepository, TrajetMapper trajetMapper, UserClient userClient) {
        this.trajetRepository = trajetRepository;
        this.trajetMapper = trajetMapper;
        this.userClient = userClient;
    }

    /**
     * Return a {@link Page} of {@link TrajetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrajetDTO> findByCriteria(TrajetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        var user = userClient.getAccount();
        log.info("User INFOS : {}", user);
        final Specification<Trajet> specification = createSpecification(criteria);
        return trajetRepository.findAll(specification, page).map(trajetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrajetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Trajet> specification = createSpecification(criteria);
        return trajetRepository.count(specification);
    }

    public List<TrajetDTO> findMyTrajectsByCriteria(TrajetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        if (criteria == null) {
            criteria = new TrajetCriteria();
        }
        criteria.setupConnectedUser();
        final Specification<Trajet> specification = createSpecification(criteria);
        return trajetMapper.toDto(trajetRepository.findAll(specification));
    }

    /**
     * Function to convert {@link TrajetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Trajet> createSpecification(TrajetCriteria criteria) {
        Specification<Trajet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Trajet_.id));
            }
            if (criteria.getPointDepart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPointDepart(), Trajet_.pointDepart));
            }
            if (criteria.getPointArrive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPointArrive(), Trajet_.pointArrive));
            }
            if (criteria.getDateHeure() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateHeure(), Trajet_.dateHeure));
            }
            if (criteria.getPlaceDispo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlaceDispo(), Trajet_.placeDispo));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), Trajet_.prix));
            }
            if (criteria.getUserLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserLogin(), Trajet_.userLogin));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), Trajet_.deleted));
            }
        }
        return specification;
    }
}
