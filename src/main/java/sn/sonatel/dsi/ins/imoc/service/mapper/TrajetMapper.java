package sn.sonatel.dsi.ins.imoc.service.mapper;

import org.mapstruct.*;
import sn.sonatel.dsi.ins.imoc.domain.Trajet;
import sn.sonatel.dsi.ins.imoc.service.dto.TrajetDTO;

import java.util.List;

/**
 * Mapper for the entity {@link Trajet} and its DTO {@link TrajetDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrajetMapper extends EntityMapper<TrajetDTO, Trajet> {

    // Conversion d'un Trajet en TrajetDTO
    TrajetDTO toDto(Trajet trajet);
    // Conversion d'une liste de Trajet en une liste de TrajetDTO
    List<TrajetDTO> toDto(List<Trajet> trajets);
}
