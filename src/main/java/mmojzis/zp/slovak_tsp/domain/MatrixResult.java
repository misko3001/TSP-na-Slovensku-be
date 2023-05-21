package mmojzis.zp.slovak_tsp.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MatrixResult {

    private List<MatrixPoint> shortestPath;

    private List<Double> distances;

    private Double fullLength;

}
