package sn.sonatel.dsi.ins.imoc.repository;

import java.time.ZonedDateTime;
import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.sonatel.dsi.ins.imoc.domain.Trajet;

/**
 * Spring Data JPA repository for the Trajet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long>, JpaSpecificationExecutor<Trajet> {
    List<Trajet> findAllByUserLoginIgnoreCase(String login);
    List<Trajet> findAllByDateHeureAfter(ZonedDateTime dateHeure);
    List<Trajet> findByDateHeureGreaterThanEqualAndDeletedFalse(ZonedDateTime currentDate);
    List<Trajet> findByUserLoginAndDateHeureGreaterThanEqualAndDeletedFalse(String userLogin, ZonedDateTime currentDate);


}
