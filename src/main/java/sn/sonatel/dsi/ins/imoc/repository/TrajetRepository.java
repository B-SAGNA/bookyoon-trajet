package sn.sonatel.dsi.ins.imoc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.sonatel.dsi.ins.imoc.domain.Trajet;
import sn.sonatel.dsi.ins.imoc.service.dto.TrajetDTO;

/**
 * Spring Data JPA repository for the Trajet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long>, JpaSpecificationExecutor<Trajet> {
    List<Trajet> findAllByUserLoginIgnoreCase(String login);
}
