package mmojzis.zp.gen_alg.controller.mapper;

import mmojzis.zp.gen_alg.controller.dto.WaypointDto;
import mmojzis.zp.gen_alg.controller.dto.slovak.MapPointDto;
import mmojzis.zp.gen_alg.controller.dto.slovak.MapRouteDto;
import mmojzis.zp.gen_alg.domain.entity.MapPoint;
import mmojzis.zp.gen_alg.domain.entity.MapRoute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapDataMapper {

    @Mapping(target = "name", source = "city")
    MapPointDto pointToDto(MapPoint point);

    MapRouteDto routeToDto(MapRoute route);

    @Mapping(target = "city", source = "name")
    MapPoint waypointToPoint(WaypointDto dto);
}
