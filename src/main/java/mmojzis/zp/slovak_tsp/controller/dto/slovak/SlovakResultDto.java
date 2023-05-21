package mmojzis.zp.slovak_tsp.controller.dto.slovak;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SlovakResultDto {

    private List<String> route;

    private Double length;

    private Long generations;

    private Double duration;
}
