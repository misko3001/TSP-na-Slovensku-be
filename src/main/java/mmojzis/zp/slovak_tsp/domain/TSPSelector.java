package mmojzis.zp.slovak_tsp.domain;

import io.jenetics.*;
import lombok.Getter;

@Getter
public enum TSPSelector {
    TOURNAMENT(TournamentSelector.class),
    BOLTZMANN(BoltzmannSelector.class),
    LINEAR_RANK(LinearRankSelector.class),
    EXPONENTIAL_RANK(ExponentialRankSelector.class),
    ROULETTE_WHEEL(RouletteWheelSelector.class),
    MONTE_CARLO(MonteCarloSelector.class),
    STOCHASTIC_UNIVERSAL(StochasticUniversalSelector.class),
    ELITE(EliteSelector.class);

    TSPSelector(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Class<?> clazz;
}
