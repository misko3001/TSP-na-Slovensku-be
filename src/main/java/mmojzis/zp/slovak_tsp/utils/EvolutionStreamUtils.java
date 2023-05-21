package mmojzis.zp.slovak_tsp.utils;

import static io.jenetics.engine.Limits.*;

import io.jenetics.EnumGene;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStream;
import io.jenetics.engine.Limits;
import io.jenetics.jpx.WayPoint;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;
import mmojzis.zp.slovak_tsp.domain.TerminationConditions;

import java.time.Duration;
import java.util.stream.Stream;

public final class EvolutionStreamUtils {

    private EvolutionStreamUtils() {
    }

    public static Stream<EvolutionResult<EnumGene<MatrixPoint>, Double>> addAllMatrixFilters(
            EvolutionStream<EnumGene<MatrixPoint>, Double> stream,
            TerminationConditions con) {

        if (con.hasSteadyFitnessCondition()) {
            stream = stream.limit(bySteadyFitness(con.getMaxSteadyFitnessGenerations()));
        }
        if (con.hasExecutionTimeCondition()) {
            stream = stream.limit(byExecutionTime(Duration.of(con.getChronoValue(), con.getChronoUnit())));
        }
        if (con.hasFitnessConvergenceCondition()) {
            stream = stream.limit(byFitnessConvergence(con.getShortFilter(), con.getLongFilter(), con.getEpsilon()));
        }
        return stream.limit(Limits.byFixedGeneration(con.getMaxGenerations()));
    }

    public static Stream<EvolutionResult<EnumGene<WayPoint>, Double>> addAllWaypointFilters(
            EvolutionStream<EnumGene<WayPoint>, Double> stream,
            TerminationConditions con) {

        if (con.hasSteadyFitnessCondition()) {
            stream = stream.limit(bySteadyFitness(con.getMaxSteadyFitnessGenerations()));
        }
        if (con.hasExecutionTimeCondition()) {
            stream = stream.limit(byExecutionTime(Duration.of(con.getChronoValue(), con.getChronoUnit())));
        }
        if (con.hasFitnessConvergenceCondition()) {
            stream = stream.limit(byFitnessConvergence(con.getShortFilter(), con.getLongFilter(), con.getEpsilon()));
        }
        return stream.limit(Limits.byFixedGeneration(con.getMaxGenerations()));
    }
}
