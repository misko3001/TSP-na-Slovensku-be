package mmojzis.zp.slovak_tsp.controller.mapper;

import mmojzis.zp.slovak_tsp.controller.dto.WaypointDto;
import mmojzis.zp.slovak_tsp.controller.dto.slovak.MapPointDto;
import mmojzis.zp.slovak_tsp.controller.dto.slovak.MapRouteDto;
import mmojzis.zp.slovak_tsp.domain.entity.MapPoint;
import mmojzis.zp.slovak_tsp.domain.entity.MapRoute;
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
