package mmojzis.zp.slovak_tsp.controller.dto.geoid;

import lombok.Data;
import mmojzis.zp.slovak_tsp.controller.dto.WaypointDto;

import java.util.List;

@Data
public class GeoidResultDto {

    private List<String> route;

    private Double length;

    private Long generations;

    private Double duration;

    private String gpx;
}
