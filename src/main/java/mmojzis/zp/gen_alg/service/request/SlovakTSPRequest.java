package mmojzis.zp.gen_alg.service.request;

import io.jenetics.util.ISeq;
import lombok.Data;
import mmojzis.zp.gen_alg.domain.MatrixPoint;

@Data
public class SlovakTSPRequest extends TSPRequest {

    private ISeq<MatrixPoint> points;
}
