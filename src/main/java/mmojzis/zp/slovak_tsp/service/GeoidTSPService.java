package mmojzis.zp.slovak_tsp.service;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static mmojzis.zp.slovak_tsp.utils.EvolutionStreamUtils.addAllWaypointFilters;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.WayPoint;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.common.EvolutionWebSocketInterceptor;
import mmojzis.zp.slovak_tsp.domain.TerminationConditions;
import mmojzis.zp.slovak_tsp.service.request.TSPRequest;
import mmojzis.zp.slovak_tsp.utils.factory.EngineFactory;
import mmojzis.zp.slovak_tsp.common.problem.GeoidTSP;
import mmojzis.zp.slovak_tsp.domain.GeoidResult;
import mmojzis.zp.slovak_tsp.service.request.GeoidTSPRequest;
import mmojzis.zp.slovak_tsp.utils.factory.InterceptorBeanFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GeoidTSPService {

    public final InterceptorBeanFactory interceptorFactory;

    public GeoidTSPService(InterceptorBeanFactory interceptorFactory) {
        this.interceptorFactory = interceptorFactory;
    }

    public GeoidResult calculate(GeoidTSPRequest request) {
        log.debug("Calculate geoidTSP with waypoints: {}", request);

        final GeoidTSP tsp = new GeoidTSP(request.getPoints());
        final Engine<EnumGene<WayPoint>, Double> engine = getEngine(tsp, request);

        final EvolutionStatistics<Double, ?> stats = EvolutionStatistics.ofNumber();
        final Phenotype<EnumGene<WayPoint>, Double> best = executeEvolution(engine, stats, request.getTermination());

        final ISeq<WayPoint> bestPath = tsp.decode(best.genotype());
        log.info("Statistics generated during execution:\n{}", stats);
        log.info("The calculated optimal path is: {}", bestPath);
        return process(best, tsp, stats);
    }

    private Phenotype<EnumGene<WayPoint>, Double> executeEvolution(Engine<EnumGene<WayPoint>, Double> engine,
                                                                      EvolutionStatistics<Double, ?> statistics,
                                                                      TerminationConditions terminationConditions) {
        return addAllWaypointFilters(engine.stream(), terminationConditions)
                .peek(statistics)
                .collect(toBestPhenotype());
    }

    private GeoidResult process(Phenotype<EnumGene<WayPoint>, Double> result,
                                GeoidTSP task,
                                EvolutionStatistics<Double, ?> stats) {
        ISeq<WayPoint> optimalPath = task.decode(result.genotype());
        final GPX gpx = GPX.builder()
                .addTrack(track -> track
                        .name("Optimal Path")
                        .addSegment(s -> s.points(optimalPath.append(optimalPath.get(0)).asList())))
                .build();
        return GeoidResult.builder()
                .route(optimalPath.stream().map(wayPoint -> wayPoint.getName().get()).toList())
                .length(task.fitness().apply(optimalPath))
                .generations(stats.altered().count())
                .duration(stats.evolveDuration().sum())
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
