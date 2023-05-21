package mmojzis.zp.gen_alg.controller;

import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.gen_alg.controller.dto.slovak.SlovakResultDto;
import mmojzis.zp.gen_alg.controller.dto.slovak.SlovakTSPRequestDto;
import mmojzis.zp.gen_alg.controller.mapper.SlovakMapper;
import mmojzis.zp.gen_alg.service.MatrixTSPService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/tsp/slovak")
public class SlovakTSPController {

    private final MatrixTSPService matrixTSPService;

    private final SlovakMapper slovakMapper;

    public SlovakTSPController(MatrixTSPService matrixTSPService, SlovakMapper slovakMapper) {
        this.matrixTSPService = matrixTSPService;
        this.slovakMapper = slovakMapper;
    }

    @PostMapping
    public SlovakResultDto calculateSlovakTSP(@Valid @RequestBody SlovakTSPRequestDto dto) {
        log.info("Calculate TSP with {} points", dto.getPoints().size());

        return slovakMapper.resultToDto(matrixTSPService.calculate(slovakMapper.dtoToRequest(dto)));
    }
}
