package mmojzis.zp.slovak_tsp.service.request;

import io.jenetics.util.ISeq;
import lombok.Data;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;

@Data
public class MatrixTSPRequest extends TSPRequest {

    private Double[][] distances;

    private ISeq<MatrixPoint> points;
}
