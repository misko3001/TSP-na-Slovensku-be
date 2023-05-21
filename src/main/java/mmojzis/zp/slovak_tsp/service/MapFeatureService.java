package mmojzis.zp.slovak_tsp.service;

import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.common.problem.MatrixTSP;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;
import mmojzis.zp.slovak_tsp.domain.entity.MapPoint;
import mmojzis.zp.slovak_tsp.domain.entity.MapRoute;
import mmojzis.zp.slovak_tsp.domain.repository.MapPointRepository;
import mmojzis.zp.slovak_tsp.domain.repository.MapRouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static mmojzis.zp.slovak_tsp.utils.CacheKeyGenerator.generateDistanceKey;

@Service
@Slf4j
public class MapFeatureService {

    private final MapPointRepository pointRepository;

    private final MapRouteRepository routeRepository;

    private final ConcurrentMap<String, Double> distanceCache;

    @PostConstruct
    @Transactional(readOnly = true)
    public void initializeDistanceCache() {
        routeRepository.getBaseRoutes()
                .parallelStream()
                .forEach(route -> distanceCache.put(generateDistanceKey(route.getStartPoint(), route.getEndPoint()),
                                                    route.getDistance()));
    }

    public MapFeatureService(MapPointRepository pointRepository, MapRouteRepository routeRepository, ConcurrentMap<String, Double> distanceCache) {
        this.pointRepository = pointRepository;
        this.routeRepository = routeRepository;
        this.distanceCache = distanceCache;
    }

    public List<MapPoint> getPoints() {
        log.debug("getPoints - retrieving points from database");
        return pointRepository.findAllPoints();
    }

    public Double[][] getDistances(ISeq<MatrixPoint> points) {
        log.info("Creating distance matrix for points: {}", points);

        Double[][] distances = new Double[points.size()][points.size()];
        for (int i = 0; i < points.size(); i++) {
            MatrixPoint start = points.get(i);
            for (int j = i; j < points.size(); j++) {
                MatrixPoint end = points.get(j);
                distances[i][j] = i == j ? MatrixTSP.INVALID : getDistance(start.getName(), end.getName());
                distances[j][i] = distances[i][j];
                log.debug("distances[{}][{}] = {}", i, j, distances[i][j]);
            }
        }
        return distances;
    }

    public MapRoute getRoute(String start, String end) {
        log.debug("Retrieving route: {} <-> {}", start, end);

        return routeRepository.findById(start, end).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entity for route %s <-> %s not found", start, end))
        );
    }

    public Double getDistance(String start, String end) {
        log.debug("Retrieving distance: {} <-> {}", start, end);

        Double val = checkDistanceCache(start, end);
        if (val != null) {
            return val;
        }

        return routeRepository.findDistanceById(start, end).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entity for route %s <-> %s not found", start, end)));
    }

    public Long countAllPoints() {
        log.debug("Counting all saved routes");
        return pointRepository.count();
    }

    public Long countAllRoutes() {
        log.debug("Counting all saved routes");
        return routeRepository.count();
    }

    private Double checkDistanceCache(String start, String end) {
        String key = generateDistanceKey(start, end);
        return distanceCache.get(key);
    }
}
