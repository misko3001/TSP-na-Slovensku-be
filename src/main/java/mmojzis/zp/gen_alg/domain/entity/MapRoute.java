package mmojzis.zp.gen_alg.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "route")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MapRouteDefinition.class)
public class MapRoute {

    @Id
    private String startPoint;

    @Id
    private String endPoint;

    private Double distance;

    @Column(columnDefinition = "text")
    private String polyline;

    public MapRoute(String startPoint, String endPoint, Double distance) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
    }
}
