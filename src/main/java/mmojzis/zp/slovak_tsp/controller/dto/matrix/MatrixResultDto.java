package mmojzis.zp.slovak_tsp.controller.dto.matrix;

import lombok.Data;

import java.util.List;

@Data
public class MatrixResultDto {

    private List<MatrixPointDto> shortestPath;

    private List<Double> distances;

    private Double fullLength;
}
