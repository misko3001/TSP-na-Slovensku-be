package mmojzis.zp.slovak_tsp.controller.dto.geoid;

import lombok.Data;
import mmojzis.zp.slovak_tsp.controller.dto.WaypointDto;

import java.util.List;

@Data
public class GeoidResultDto {

    private List<WaypointDto> shortestPath;

    private List<Double> distances;

    private Double fullLength;

    private String gpx;
}
