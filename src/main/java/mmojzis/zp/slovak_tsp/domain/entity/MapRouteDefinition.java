package mmojzis.zp.slovak_tsp.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MapRouteDefinition implements Serializable {

    private String startPoint;

    private String endPoint;

    public MapRouteDefinition(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
