package mmojzis.zp.slovak_tsp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TempTSPResult {

    private Long generation;

    private List<String> route;

    private Double length;
}
