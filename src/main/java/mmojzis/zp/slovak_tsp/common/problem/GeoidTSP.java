package mmojzis.zp.slovak_tsp.common.problem;

import io.jenetics.*;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.jpx.Length;
import io.jenetics.jpx.WayPoint;
import io.jenetics.jpx.geom.Geoid;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.common.CycleCrossover;
import mmojzis.zp.slovak_tsp.common.InversionMutator;
import mmojzis.zp.slovak_tsp.domain.TSPCrossover;
import mmojzis.zp.slovak_tsp.domain.TSPMutator;
import mmojzis.zp.slovak_tsp.domain.TSPSelector;
import org.springframework.util.Assert;

import java.util.function.Function;

@Slf4j
public final class GeoidTSP implements Problem<ISeq<WayPoint>, EnumGene<WayPoint>, Double> {

    private final ISeq<WayPoint> points;

    public GeoidTSP(ISeq<WayPoint> points) {
        log.debug("Creating GeoidTSP with points: {}", points);

        if (points == null || points.stream().count() < 2) {
            throw new IllegalArgumentException("GeoidTSP must have at least 2 points");
        }
        this.points = points;
    }

    @Override
    public Codec<ISeq<WayPoint>, EnumGene<WayPoint>> codec() {
        return Codecs.ofPermutation(points);
    }

    @Override
    public Function<ISeq<WayPoint>, Double> fitness() {
        return point -> point.stream().collect(Geoid.DEFAULT.toTourLength()).to(Length.Unit.METER);
    }

    public static Selector<EnumGene<WayPoint>, Double> createSelector(TSPSelector selector,
                                                                      String modifier,
                                                                      Integer numberOfElites) {
        Selector<EnumGene<WayPoint>, Double> result;
        switch (selector) {
            case TOURNAMENT -> {
                Assert.hasText(modifier, "No tournament size provided for tournament selector");
                int tournamentSize = Integer.parseInt(modifier);
                result = new TournamentSelector<>(tournamentSize);
            }
            case BOLTZMANN -> {
                Assert.hasText(modifier, "No beta value provided for Boltzmann selector");
                double beta = Double.parseDouble(modifier);
                result = new BoltzmannSelector<>(beta);
            }
            case LINEAR_RANK -> {
                Assert.hasText(modifier, "No n-minus value provided for linear rank selector");
                double nminus = Double.parseDouble(modifier);
                result = new LinearRankSelector<>(nminus);
            }
            case EXPONENTIAL_RANK -> {
                Assert.hasText(modifier, "No modifier value provided for exponential rank selector");
                double mod = Double.parseDouble(modifier);
                result = new ExponentialRankSelector<>(mod);
            }
            case ROULETTE_WHEEL -> result = new RouletteWheelSelector<>();
            case MONTE_CARLO -> result = new MonteCarloSelector<>();
            case STOCHASTIC_UNIVERSAL -> result = new StochasticUniversalSelector<>();
            default -> throw new IllegalArgumentException("Invalid selector requested: " + selector);
        }
        if (numberOfElites != null) {
            return new EliteSelector<>(numberOfElites, result);
        }
        return result;
    }

    public static Mutator<EnumGene<WayPoint>, Double> createMutator(TSPMutator mutator, String modifier) {
        switch (mutator) {
            case SWAP -> {
                Assert.hasText(modifier, "No probability provided for swap mutator");
                double probability = Double.parseDouble(modifier);
                return new SwapMutator<>(probability);
            } case INVERSION -> {
                Assert.hasText(modifier, "No probability provided for inversion mutator");
                double probability = Double.parseDouble(modifier);
                return new InversionMutator<>(probability);
            }
            default -> throw new IllegalArgumentException("Invalid mutator requested: " + mutator);
        }
    }

    public static Crossover<EnumGene<WayPoint>, Double> createCrossover(TSPCrossover crossover, String modifier) {
        switch (crossover) {
            case PMX -> {
                Assert.hasText(modifier, "No probability provided for PMX crossover");
                double probability = Double.parseDouble(modifier);
                return new PartiallyMatchedCrossover<>(probability);
            } case CX -> {
                Assert.hasText(modifier, "No probability provided for CX crossover");
                double probability = Double.parseDouble(modifier);
                return new CycleCrossover<>(probability);
            }
            default -> throw new IllegalArgumentException("Invalid crossover requested: " + crossover);
        }
    }
}
