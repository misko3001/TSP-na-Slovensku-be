package mmojzis.zp.gen_alg.service;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static mmojzis.zp.gen_alg.utils.EvolutionStreamUtils.addAllWaypointFilters;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Length;
import io.jenetics.jpx.WayPoint;
import io.jenetics.jpx.geom.Geoid;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.gen_alg.common.EvolutionWebSocketInterceptor;
import mmojzis.zp.gen_alg.domain.TerminationConditions;
import mmojzis.zp.gen_alg.service.request.TSPRequest;
import mmojzis.zp.gen_alg.utils.factory.EngineFactory;
import mmojzis.zp.gen_alg.common.problem.GeoidTSP;
import mmojzis.zp.gen_alg.domain.GeoidResult;
import mmojzis.zp.gen_alg.service.request.GeoidTSPRequest;
import mmojzis.zp.gen_alg.utils.factory.InterceptorBeanFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GeoidTSPService {

    public final InterceptorBeanFactory interceptorFactory;

    public GeoidTSPService(InterceptorBeanFactory interceptorFactory) {
        this.interceptorFactory = interceptorFactory;
    }

    public GeoidResult calculate(GeoidTSPRequest request) {
        log.debug("Calculate geoidTSP with waypoints: {}", request);

        final GeoidTSP tsp = new GeoidTSP(request.getWaypoints());
        final Engine<EnumGene<WayPoint>, Double> engine = getEngine(tsp, request);

        final EvolutionStatistics<Double, ?> stats = EvolutionStatistics.ofNumber();
        final Phenotype<EnumGene<WayPoint>, Double> best = executeEvolution(engine, stats, request.getTermination());

        final ISeq<WayPoint> bestPath = tsp.decode(best.genotype());
        log.info("Statistics generated during execution:\n{}", stats);
        log.info("The calculated optimal path is: {}", bestPath);
        return process(bestPath);
    }

    private Phenotype<EnumGene<WayPoint>, Double> executeEvolution(Engine<EnumGene<WayPoint>, Double> engine,
                                                                      EvolutionStatistics<Double, ?> statistics,
                                                                      TerminationConditions terminationConditions) {
        return addAllWaypointFilters(engine.stream(), terminationConditions)
                .peek(statistics)
                .collect(toBestPhenotype());
    }

    private GeoidResult process(ISeq<WayPoint> optimalPath) {
        List<Double> distances = new ArrayList<>();
        double km = 0L;
        int size = optimalPath.size();
        for (int i = 0; i < size; i++) {
            double length = Geoid.DEFAULT
                    .distance(optimalPath.get(i % size), optimalPath.get((i + 1) % size)).to(Length.Unit.METER) / 1000;
            distances.add(length);
            km += length;
        }

        final GPX gpx = GPX.builder()
                .addTrack(track -> track
                        .name("Optimal Path")
                        .addSegment(s -> s.points(optimalPath.append(optimalPath.get(0)).asList())))
                .build();

        return GeoidResult.builder()
                .shortestPath(optimalPath.stream().toList())
                .distances(distances.stream().toList())
                .fullLength(km)
                .gpx(GPX.Writer.DEFAULT.toString(gpx))
                .build();
    }

    private Engine<EnumGene<WayPoint>, Double> getEngine(GeoidTSP task, TSPRequest request) {
        if (request.hasInterceptor()) {
            return EngineFactory.createWayPointEngine(task,
                    request,
                    getInterceptor(task, request.getProcessId(), request.getPublishEachGeneration()));
        }
        return EngineFactory.createWayPointEngine(task, request);
    }

    private EvolutionWebSocketInterceptor<ISeq<WayPoint>, EnumGene<WayPoint>, Double> getInterceptor(
            GeoidTSP task, String processId, Long publishEachGeneration) {
        return interceptorFactory.createInterceptor(task, processId, publishEachGeneration);
    }
}
