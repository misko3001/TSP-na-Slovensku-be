package mmojzis.zp.gen_alg.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SlovakResult {

    private List<String> route;

    private Double length;

    private Long generations;

    private Double duration;
}
