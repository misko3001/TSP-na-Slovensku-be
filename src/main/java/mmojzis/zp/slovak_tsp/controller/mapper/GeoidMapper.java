package mmojzis.zp.slovak_tsp.controller.mapper;

import io.jenetics.jpx.WayPoint;
import io.jenetics.util.ISeq;
import mmojzis.zp.slovak_tsp.controller.dto.geoid.GeoidTSPRequestDto;
import mmojzis.zp.slovak_tsp.controller.dto.geoid.GeoidResultDto;
import mmojzis.zp.slovak_tsp.controller.dto.WaypointDto;
import mmojzis.zp.slovak_tsp.domain.GeoidResult;
import mmojzis.zp.slovak_tsp.service.request.GeoidTSPRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = GeneralMapper.class)
public interface GeoidMapper {

    GeoidResultDto resultToDto(GeoidResult result);

    GeoidTSPRequest geoidDtoToRequest(GeoidTSPRequestDto dto);

    @Mapping(target = "lat", source = "latitude")
    @Mapping(target = "lon", source = "longitude")
    WayPoint dtoToWayPoint(WaypointDto dto);

    @Mapping(target = "latitude", expression = "java(point.getLatitude().doubleValue())")
    @Mapping(target = "longitude", expression = "java(point.getLongitude().doubleValue())")
    @Mapping(target = "name", expression = "java(point.getName().get())")
    WaypointDto wayPointToDto(WayPoint point);

    default ISeq<WayPoint> waypointListToISeq(List<WaypointDto> waypoints) {
        return waypoints.stream().map(this::dtoToWayPoint).collect(ISeq.toISeq());
    }

    @AfterMapping
    default void checkIfWaypointsAreUnique(@MappingTarget GeoidTSPRequest request) {
        ISeq<WayPoint> waypoints = request.getPoints();

        long distinct = waypoints.stream().map(WayPoint::getName).distinct().count();
        if (distinct < waypoints.stream().count()) {
            throw new IllegalArgumentException("Some waypoints do not have unique names");
        }

        for (WayPoint point1 : waypoints) {
            for (WayPoint point2 : waypoints) {
                if (point1 != point2
                        && point1.getLatitude().doubleValue() == point2.getLatitude().doubleValue()
                        && point1.getLongitude().doubleValue() == point2.getLongitude().doubleValue()) {
                    throw new IllegalArgumentException("Some waypoints do not have unique coordinates");
                }
            }
        }
    }
}
