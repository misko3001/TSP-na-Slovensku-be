package mmojzis.zp.slovak_tsp.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeoidResult {

    private List<String> route;

    private Double length;

    private Long generations;

    private Double duration;

    private String gpx;
}
