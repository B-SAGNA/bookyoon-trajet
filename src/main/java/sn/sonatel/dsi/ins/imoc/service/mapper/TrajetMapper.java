package sn.sonatel.dsi.ins.imoc.service.mapper;

import org.mapstruct.*;
import sn.sonatel.dsi.ins.imoc.domain.Trajet;
import sn.sonatel.dsi.ins.imoc.service.dto.TrajetDTO;

/**
 * Mapper for the entity {@link Trajet} and its DTO {@link TrajetDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrajetMapper extends EntityMapper<TrajetDTO, Trajet> {}
