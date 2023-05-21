package mmojzis.zp.gen_alg.controller.dto.geoid;

import lombok.Data;
import mmojzis.zp.gen_alg.controller.dto.WaypointDto;

import java.util.List;

@Data
public class GeoidResultDto {

    private List<WaypointDto> shortestPath;

    private List<Double> distances;

    private Double fullLength;

    private String gpx;
}
