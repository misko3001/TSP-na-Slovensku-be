package mmojzis.zp.slovak_tsp.controller;

import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.controller.dto.geoid.GeoidTSPRequestDto;
import mmojzis.zp.slovak_tsp.controller.dto.geoid.GeoidResultDto;
import mmojzis.zp.slovak_tsp.controller.mapper.GeoidMapper;
import mmojzis.zp.slovak_tsp.service.GeoidTSPService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/tsp/geoid")
public class GeoidTSPController {

    private final GeoidTSPService geoidTSPService;

    private final GeoidMapper geoidMapper;

    public GeoidTSPController(GeoidTSPService geoidTSPService, GeoidMapper geoidMapper) {
        this.geoidTSPService = geoidTSPService;
        this.geoidMapper = geoidMapper;
    }

    @PostMapping
    public GeoidResultDto calculateGeoidTSP(@Valid @RequestBody GeoidTSPRequestDto dto) {
        log.info("Calculate TSP with {} waypoints", dto.getWaypoints().size());

        return geoidMapper.resultToDto(geoidTSPService.calculate(geoidMapper.geoidDtoToRequest(dto)));
    }
}
