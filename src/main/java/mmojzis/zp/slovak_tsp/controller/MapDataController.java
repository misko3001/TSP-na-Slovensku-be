package mmojzis.zp.slovak_tsp.controller;

import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.controller.dto.slovak.MapPointDto;
import mmojzis.zp.slovak_tsp.controller.dto.slovak.MapRouteDto;
import mmojzis.zp.slovak_tsp.controller.mapper.MapDataMapper;
import mmojzis.zp.slovak_tsp.service.MapFeatureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/slovakia")
@Slf4j
public class MapDataController {

    private final MapFeatureService featureService;

    private final MapDataMapper mapper;

    public MapDataController(MapFeatureService featureService, MapDataMapper mapper) {
        this.featureService = featureService;
        this.mapper = mapper;
    }

    @GetMapping("/points")
    public List<MapPointDto> getPoints() {
        log.debug("getPoints request received");

        return featureService.getPoints().stream()
                .map(mapper::pointToDto)
                .toList();
    }

    @GetMapping("/routes/{start}/{end}")
    public MapRouteDto getRoute(@PathVariable String start, @PathVariable String end) {
        log.debug("getRoute request received - from {} to {}", start, end);

        return mapper.routeToDto(featureService.getRoute(start, end));
    }
}
