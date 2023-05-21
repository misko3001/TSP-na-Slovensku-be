package mmojzis.zp.slovak_tsp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatrixPoint {

    private String name;

    private Integer index;
}
