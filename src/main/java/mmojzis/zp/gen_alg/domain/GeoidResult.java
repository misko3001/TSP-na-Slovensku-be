package mmojzis.zp.gen_alg.domain;

import io.jenetics.jpx.WayPoint;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeoidResult {

    private List<WayPoint> shortestPath;

    private List<Double> distances;

    private Double fullLength;

    private String gpx;
}
