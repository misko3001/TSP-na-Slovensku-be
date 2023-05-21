package mmojzis.zp.slovak_tsp.common;

import static mmojzis.zp.slovak_tsp.controller.TSPWebsocketController.TSP_SUBSCRIPTION_URL;

import io.jenetics.Gene;
import io.jenetics.Phenotype;
import io.jenetics.engine.EvolutionInterceptor;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Problem;
import io.jenetics.jpx.WayPoint;
import io.jenetics.util.ISeq;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.common.problem.MatrixTSP;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;
import mmojzis.zp.slovak_tsp.domain.TempTSPResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
@Data
public final class EvolutionWebSocketInterceptor<T, G extends Gene<?, G>, C extends Comparable<? super C>>
        implements EvolutionInterceptor<G, C> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Problem<T, G, C> task;

    private String processId;

    private Long publishEachGeneration;

    @Override
    public EvolutionResult<G, C> after(EvolutionResult<G, C> result) {
        if ((result.generation() - 1) % publishEachGeneration == 0) {
            String url = getPublishUrl();
            log.debug("Sending data to '{}'", url);
            messagingTemplate.convertAndSend(url, getData(result));
        }
        return result;
    }

    private TempTSPResult getData(EvolutionResult<G, C> result) {
        Phenotype<G, C> best = result.bestPhenotype();
        T bestPath = task.decode(best.genotype());
        C length = task.fitness().apply(bestPath);


        if (task instanceof MatrixTSP) {
            ISeq<MatrixPoint> route = (ISeq<MatrixPoint>) bestPath;
            route = route.append(route.get(0));
            return TempTSPResult.builder()
                    .route(route.stream().map(MatrixPoint::getName).toList())
                    .generation(result.generation() - 1)
                    .length((Double) length)
                    .build();
        } else {
            ISeq<WayPoint> route = (ISeq<WayPoint>) bestPath;
            route = route.append(route.get(0));
            return TempTSPResult.builder()
                    .route(route.stream().map(point -> point.getName().orElseThrow(
                            () -> new IllegalStateException("Unable to map waypoint"))).toList())
                    .generation(result.generation() - 1)
                    .length((Double) length)
                    .build();
        }
    }

    private String getPublishUrl() {
        return TSP_SUBSCRIPTION_URL + processId;
    }
}
