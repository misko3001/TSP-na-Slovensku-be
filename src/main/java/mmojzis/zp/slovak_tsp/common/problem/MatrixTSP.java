package mmojzis.zp.slovak_tsp.common.problem;

import io.jenetics.*;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.common.CycleCrossover;
import mmojzis.zp.slovak_tsp.common.InversionMutator;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;
import mmojzis.zp.slovak_tsp.domain.TSPCrossover;
import mmojzis.zp.slovak_tsp.domain.TSPMutator;
import mmojzis.zp.slovak_tsp.domain.TSPSelector;
import org.springframework.util.Assert;

import java.util.function.Function;

@Slf4j
public final class MatrixTSP implements Problem<ISeq<MatrixPoint>, EnumGene<MatrixPoint>, Double> {

    public static final Double INVALID = null;

    private final Double[][] distances;

    private final ISeq<MatrixPoint> points;

    public MatrixTSP(Double[][] distances, ISeq<MatrixPoint> points) {
        log.debug("Creating MatrixTSP with dimensions: {} x {}", distances[0].length, distances.length);

        if (points == null || points.stream().count() < 2) {
            throw new IllegalArgumentException("MatrixTSP must have at least 2 points");
        } else if (distances[0].length != points.length() || distances.length != points.length()) {
            throw new IllegalArgumentException("Incorrect matrix dimensions for MatrixTSP");
        }
        this.points = points;
        this.distances = distances;
    }

    @Override
    public Codec<ISeq<MatrixPoint>, EnumGene<MatrixPoint>> codec() {
        return Codecs.ofPermutation(points);
    }

    @Override
    public Function<ISeq<MatrixPoint>, Double> fitness() {
        return candidate -> {
            double distance = 0;
            int size = candidate.size();
            for (int i = 0; i < size; i++) {
                distance += getDistance(candidate.get(i % size), candidate.get((i + 1) % size));
            }
            return distance;
        };
    }

    private Double getDistance(MatrixPoint p1, MatrixPoint p2) {
        return distances[p1.getIndex()][p2.getIndex()];
    }

    public static Selector<EnumGene<MatrixPoint>, Double> createSelector(TSPSelector selector,
                                                                      String modifier,
                                                                      Integer numberOfElites) {
        Selector<EnumGene<MatrixPoint>, Double> result;
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

    public static Mutator<EnumGene<MatrixPoint>, Double> createMutator(TSPMutator mutator, String modifier) {
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

    public static Crossover<EnumGene<MatrixPoint>, Double> createCrossover(TSPCrossover crossover, String modifier) {
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
