package mmojzis.zp.gen_alg.service.request;

import io.jenetics.jpx.WayPoint;
import io.jenetics.util.ISeq;
import lombok.Data;

@Data
public class GeoidTSPRequest extends TSPRequest {

    private ISeq<WayPoint> waypoints;

}
