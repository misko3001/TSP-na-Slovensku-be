package mmojzis.zp.slovak_tsp.controller;

import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.controller.dto.matrix.MatrixResultDto;
import mmojzis.zp.slovak_tsp.controller.dto.matrix.MatrixTSPRequestDto;
import mmojzis.zp.slovak_tsp.controller.mapper.MatrixMapper;
import mmojzis.zp.slovak_tsp.service.MatrixTSPService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/tsp/matrix")
public class MatrixTSPController {

    private final MatrixTSPService matrixTSPService;

    private final MatrixMapper matrixMapper;

    public MatrixTSPController(MatrixTSPService matrixTSPService, MatrixMapper matrixMapper) {
        this.matrixTSPService = matrixTSPService;
        this.matrixMapper = matrixMapper;
    }

    @PostMapping
    public MatrixResultDto calculateMatrixTSP(@Valid @RequestBody MatrixTSPRequestDto dto) {
        log.info("Calculate TSP with {} points", dto.getPoints().size());

        return matrixMapper.resultToDto(matrixTSPService.calculate(matrixMapper.matrixDtoToRequest(dto)));
    }
}
