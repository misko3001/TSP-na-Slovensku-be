package mmojzis.zp.gen_alg.controller.dto.slovak;

import lombok.Data;

@Data
public class MapRouteDto {

    private String startPoint;

    private String endPoint;

    private Double distance;

    private String polyline;
}
