package mmojzis.zp.slovak_tsp.service.request;

import io.jenetics.util.ISeq;
import lombok.Data;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;

@Data
public class SlovakTSPRequest extends TSPRequest {

    private ISeq<MatrixPoint> points;
}
