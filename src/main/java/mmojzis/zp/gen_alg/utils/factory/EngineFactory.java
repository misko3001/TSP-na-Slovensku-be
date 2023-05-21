package mmojzis.zp.gen_alg.utils.factory;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.jpx.WayPoint;
import io.jenetics.util.ISeq;
import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.gen_alg.common.EvolutionWebSocketInterceptor;
import mmojzis.zp.gen_alg.common.problem.GeoidTSP;
import mmojzis.zp.gen_alg.common.problem.MatrixTSP;
import mmojzis.zp.gen_alg.domain.MatrixPoint;
import mmojzis.zp.gen_alg.service.request.TSPRequest;
import mmojzis.zp.gen_alg.utils.exception.EngineCreationException;

@Slf4j
public final class EngineFactory {

    private EngineFactory() {
    }

    public static Engine<EnumGene<WayPoint>, Double> createWayPointEngine(GeoidTSP task, TSPRequest request) throws EngineCreationException {
        try {
            Selector<EnumGene<WayPoint>, Double> offspringSelector = GeoidTSP.createSelector(request.getSelectors().get(0),
                    request.getSelectionModifiers().get(0),
                    request.getNumberOfElites().get(0));
            Selector<EnumGene<WayPoint>, Double> survivorSelector;
            if (request.getSelectors().get(1) != null) {
                survivorSelector = GeoidTSP.createSelector(request.getSelectors().get(1),
                        request.getSelectionModifiers().get(1),
                        request.getNumberOfElites().get(1));
            } else {
                survivorSelector = offspringSelector;
            }
            return Engine.builder(task)
                    .populationSize(request.getPopulationSize())
                    .offspringFraction(request.getOffspringFraction())
                    .offspringSelector(offspringSelector)
                    .survivorsSelector(survivorSelector)
                    .alterers(
                            GeoidTSP.createMutator(request.getMutator(), request.getMutationModifier()),
                            GeoidTSP.createCrossover(request.getCrossover(), request.getCrossoverModifier()))
                    .minimizing()
                    .build();
        } catch (Exception e) {
            log.error("Error while creating new waypoint engine: ", e);
            throw new EngineCreationException();
        }
    }

    public static Engine<EnumGene<WayPoint>, Double> createWayPointEngine(GeoidTSP task,
                                                                          TSPRequest request,
                                                                          EvolutionWebSocketInterceptor<ISeq<WayPoint>,EnumGene<WayPoint>, Double> interceptor)
            throws EngineCreationException {
        try {
            Selector<EnumGene<WayPoint>, Double> offspringSelector = GeoidTSP.createSelector(request.getSelectors().get(0),
                    request.getSelectionModifiers().get(0),
                    request.getNumberOfElites().get(0));
            Selector<EnumGene<WayPoint>, Double> survivorSelector;
            if (request.getSelectors().get(1) != null) {
                survivorSelector = GeoidTSP.createSelector(request.getSelectors().get(1),
                        request.getSelectionModifiers().get(1),
                        request.getNumberOfElites().get(1));
            } else {
                survivorSelector = offspringSelector;
            }
            return Engine.builder(task)
                    .interceptor(interceptor)
                    .populationSize(request.getPopulationSize())
                    .offspringFraction(request.getOffspringFraction())
                    .offspringSelector(offspringSelector)
                    .survivorsSelector(survivorSelector)
                    .alterers(
                            GeoidTSP.createMutator(request.getMutator(), request.getMutationModifier()),
                            GeoidTSP.createCrossover(request.getCrossover(), request.getCrossoverModifier()))
                    .minimizing()
                    .build();
        } catch (Exception e) {
            log.error("Error while creating new waypoint engine: ", e);
            throw new EngineCreationException();
        }
    }

    public static Engine<EnumGene<MatrixPoint>, Double> createMatrixPointEngine(MatrixTSP task, TSPRequest request) throws EngineCreationException {
        try {
            Selector<EnumGene<MatrixPoint>, Double> offspringSelector = MatrixTSP.createSelector(request.getSelectors().get(0),
                    request.getSelectionModifiers().get(0),
                    request.getNumberOfElites().get(0));
            Selector<EnumGene<MatrixPoint>, Double> survivorSelector;
            if (request.getSelectors().get(1) != null) {
                survivorSelector = MatrixTSP.createSelector(request.getSelectors().get(1),
                        request.getSelectionModifiers().get(1),
                        request.getNumberOfElites().get(1));
            } else {
                survivorSelector = offspringSelector;
            }
            return Engine.builder(task)
                    .populationSize(request.getPopulationSize())
                    .offspringFraction(request.getOffspringFraction())
                    .offspringSelector(offspringSelector)
                    .survivorsSelector(survivorSelector)
                    .alterers(
                            MatrixTSP.createMutator(request.getMutator(), request.getMutationModifier()),
                            MatrixTSP.createCrossover(request.getCrossover(), request.getCrossoverModifier()))
                    .minimizing()
                    .build();
        } catch (Exception e) {
            log.error("Error while creating new waypoint engine: ", e);
            throw new EngineCreationException();
        }
    }

    public static Engine<EnumGene<MatrixPoint>, Double> createMatrixPointEngine(MatrixTSP task,
                                                                                TSPRequest request,
                                                                                EvolutionWebSocketInterceptor<ISeq<MatrixPoint>,EnumGene<MatrixPoint>, Double> interceptor)
            throws EngineCreationException {
        try {
            Selector<EnumGene<MatrixPoint>, Double> offspringSelector = MatrixTSP.createSelector(request.getSelectors().get(0),
                    request.getSelectionModifiers().get(0),
                    request.getNumberOfElites().get(0));
            Selector<EnumGene<MatrixPoint>, Double> survivorSelector;
            if (request.getSelectors().get(1) != null) {
                survivorSelector = MatrixTSP.createSelector(request.getSelectors().get(1),
                        request.getSelectionModifiers().get(1),
                        request.getNumberOfElites().get(1));
            } else {
                survivorSelector = offspringSelector;
            }
            return Engine.builder(task)
                    .interceptor(interceptor)
                    .populationSize(request.getPopulationSize())
                    .offspringFraction(request.getOffspringFraction())
                    .offspringSelector(offspringSelector)
                    .survivorsSelector(survivorSelector)
                    .alterers(
                            MatrixTSP.createMutator(request.getMutator(), request.getMutationModifier()),
                            MatrixTSP.createCrossover(request.getCrossover(), request.getCrossoverModifier()))
                    .minimizing()
                    .build();
        } catch (Exception e) {
            log.error("Error while creating new waypoint engine: ", e);
            throw new EngineCreationException();
        }
    }

}
