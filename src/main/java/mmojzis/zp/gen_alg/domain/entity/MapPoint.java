package mmojzis.zp.gen_alg.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "point")
@Data
public class MapPoint {

    @Id
    private String city;

    private Float latitude;

    private Float longitude;
}
