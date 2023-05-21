package mmojzis.zp.slovak_tsp.service;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.common.EvolutionWebSocketInterceptor;
import mmojzis.zp.slovak_tsp.domain.TerminationConditions;
import mmojzis.zp.slovak_tsp.utils.factory.EngineFactory;
import mmojzis.zp.slovak_tsp.common.problem.MatrixTSP;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;
import mmojzis.zp.slovak_tsp.domain.MatrixResult;
import mmojzis.zp.slovak_tsp.domain.SlovakResult;
import mmojzis.zp.slovak_tsp.service.request.MatrixTSPRequest;
import mmojzis.zp.slovak_tsp.service.request.SlovakTSPRequest;
import mmojzis.zp.slovak_tsp.service.request.TSPRequest;
import mmojzis.zp.slovak_tsp.utils.factory.InterceptorBeanFactory;
import org.springframework.stereotype.Service;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static mmojzis.zp.slovak_tsp.utils.EvolutionStreamUtils.addAllMatrixFilters;

@Service
@Slf4j
public class MatrixTSPService {

    public final MapFeatureService mapService;

    public final InterceptorBeanFactory interceptorFactory;

    public MatrixTSPService(MapFeatureService mapService, InterceptorBeanFactory interceptorFactory) {
        this.mapService = mapService;
        this.interceptorFactory = interceptorFactory;
    }

    public MatrixResult calculate(MatrixTSPRequest request) {
        log.debug("Calculate MatrixTSP for request: {}", request);

        final MatrixTSP task = new MatrixTSP(request.getDistances(), request.getPoints());
        final EvolutionStatistics<Double, ?> stats = EvolutionStatistics.ofNumber();
        Phenotype<EnumGene<MatrixPoint>, Double> best = processEvolution(task, request, stats);

        return processMatrix(task, task.decode(best.genotype()));
    }

    public SlovakResult calculate(SlovakTSPRequest request) {
        log.debug("Calculate slovakTSP for request: {}", request);

        Double[][] distances = mapService.getDistances(request.getPoints());
        final MatrixTSP task = new MatrixTSP(distances, request.getPoints());
        final EvolutionStatistics<Double, ?> stats = EvolutionStatistics.ofNumber();
        Phenotype<EnumGene<MatrixPoint>, Double> best = processEvolution(task, request, stats);

        log.debug("The calculated optimal path is: {}", task.decode(best.genotype()));
        return processSlovak(best, task, stats);
    }

    private Phenotype<EnumGene<MatrixPoint>, Double> processEvolution(MatrixTSP task, TSPRequest request,EvolutionStatistics<Double, ?> stats) {
        log.debug("Running genetic algorithm with task: {}, for request: {}", task, request);

        final Engine<EnumGene<MatrixPoint>, Double> engine = getEngine(task, request);

        Phenotype<EnumGene<MatrixPoint>, Double> best = executeEvolution(engine, stats, request.getTermination());
        log.info("Statistics generated during execution:\n{}", stats);
        return best;
    }

    private MatrixResult processMatrix(MatrixTSP task, ISeq<MatrixPoint> optimalPath) {
        return MatrixResult.builder()
                .shortestPath(optimalPath.stream().toList())
                .fullLength(task.fitness().apply(optimalPath))
                .build();
    }

    private SlovakResult processSlovak(Phenotype<EnumGene<MatrixPoint>, Double> result,
                                       MatrixTSP task,
                                       EvolutionStatistics<Double, ?> stats) {
        ISeq<MatrixPoint> bestRoute = task.decode(result.genotype());
        Double length = task.fitness().apply(bestRoute);
        bestRoute = bestRoute.append(bestRoute.get(0));
        return SlovakResult.builder()
                .route(bestRoute.stream().map(MatrixPoint::getName).toList())
                .length(length)
                .generations(stats.altered().count())
                .duration(stats.evolveDuration().sum())
                .build();
    }

    private Phenotype<EnumGene<MatrixPoint>, Double> executeEvolution(Engine<EnumGene<MatrixPoint>, Double> engine,
                                                                      EvolutionStatistics<Double, ?> statistics,
                                                                      TerminationConditions terminationConditions) {
        return addAllMatrixFilters(engine.stream(), terminationConditions)
                .peek(statistics)
                .collect(toBestPhenotype());
    }

    private Engine<EnumGene<MatrixPoint>, Double> getEngine(MatrixTSP task, TSPRequest request) {
        if (request.hasInterceptor()) {
            return EngineFactory.createMatrixPointEngine(task,
                    request,
                    getInterceptor(task, request.getProcessId(), request.getPublishEachGeneration()));
        }
        return EngineFactory.createMatrixPointEngine(task, request);
    }

    private EvolutionWebSocketInterceptor<ISeq<MatrixPoint>, EnumGene<MatrixPoint>, Double> getInterceptor(
            MatrixTSP task, String processId, Long publishEachGeneration) {
        return interceptorFactory.createInterceptor(task, processId, publishEachGeneration);
    }
}
