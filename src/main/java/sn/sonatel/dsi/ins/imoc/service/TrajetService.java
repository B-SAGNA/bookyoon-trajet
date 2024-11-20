package sn.sonatel.dsi.ins.imoc.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.sonatel.dsi.ins.imoc.domain.Trajet;
import sn.sonatel.dsi.ins.imoc.repository.TrajetRepository;
import sn.sonatel.dsi.ins.imoc.security.SecurityUtils;
import sn.sonatel.dsi.ins.imoc.service.dto.TrajetDTO;
import sn.sonatel.dsi.ins.imoc.service.mapper.TrajetMapper;

/**
 * Service Implementation for managing {@link sn.sonatel.dsi.ins.imoc.domain.Trajet}.
 */
@Service
@Transactional
public class TrajetService {

    private final Logger log = LoggerFactory.getLogger(TrajetService.class);

    private final TrajetRepository trajetRepository;

    private final TrajetMapper trajetMapper;

    public TrajetService(TrajetRepository trajetRepository, TrajetMapper trajetMapper) {
        this.trajetRepository = trajetRepository;
        this.trajetMapper = trajetMapper;
    }

    /**
     * Save a trajet.
     *
     * @param trajetDTO the entity to save.
     * @return the persisted entity.
     */
    public TrajetDTO save(TrajetDTO trajetDTO) {
        log.debug("Request to save Trajet : {}", trajetDTO);
        Trajet trajet = trajetMapper.toEntity(trajetDTO);
        trajet = trajetRepository.save(trajet);
        return trajetMapper.toDto(trajet);
    }

    /**
     * Update a trajet.
     *
     * @param trajetDTO the entity to save.
     * @return the persisted entity.
     */
    public TrajetDTO update(TrajetDTO trajetDTO) {
        log.debug("Request to update Trajet : {}", trajetDTO);
        Trajet trajet = trajetMapper.toEntity(trajetDTO);
        trajet = trajetRepository.save(trajet);
        return trajetMapper.toDto(trajet);
    }

    /**
     * Partially update a trajet.
     *
     * @param trajetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrajetDTO> partialUpdate(TrajetDTO trajetDTO) {
        log.debug("Request to partially update Trajet : {}", trajetDTO);

        return trajetRepository
            .findById(trajetDTO.getId())
            .map(existingTrajet -> {
                trajetMapper.partialUpdate(existingTrajet, trajetDTO);

                return existingTrajet;
            })
            .map(trajetRepository::save)
            .map(trajetMapper::toDto);
    }

    /**
     * Get one trajet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrajetDTO> findOne(Long id) {
        log.debug("Request to get Trajet : {}", id);
        return trajetRepository.findById(id).map(trajetMapper::toDto);
    }

    /**
     * Delete the trajet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Trajet : {}", id);
        trajetRepository.deleteById(id);
    }

    // RETOURNE L'ENSEMBLE DES TRAJETS DE L'UTILISATEUR CONNECTE
    public List<TrajetDTO> getTrajetsHistory() {
        return trajetMapper.toDto(trajetRepository.findAllByUserLoginIgnoreCase(SecurityUtils.getCurrentUserLogin().orElseThrow()));
    }
}
