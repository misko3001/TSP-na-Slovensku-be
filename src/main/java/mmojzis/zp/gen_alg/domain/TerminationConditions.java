package mmojzis.zp.gen_alg.domain;

import lombok.Data;
import lombok.NonNull;

import java.time.temporal.ChronoUnit;

@Data
public class TerminationConditions {

    @NonNull
    private Long maxGenerations;

    private Integer maxSteadyFitnessGenerations;

    private ChronoUnit chronoUnit;

    private Long chronoValue;

    private Integer shortFilter;

    private Integer longFilter;

    private Double epsilon;

    public boolean hasSteadyFitnessCondition() {
        return maxSteadyFitnessGenerations != null;
    }

    public boolean hasExecutionTimeCondition() {
        return chronoUnit != null && chronoValue != null;
    }

    public boolean hasFitnessConvergenceCondition() {
        return shortFilter != null && longFilter != null && epsilon != null;
    }
}
