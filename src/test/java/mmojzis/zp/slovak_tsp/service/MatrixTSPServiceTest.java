package mmojzis.zp.slovak_tsp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static mmojzis.zp.slovak_tsp.utils.RandomUtils.getRandomUniqueElements;

import io.jenetics.util.ISeq;
import mmojzis.zp.slovak_tsp.controller.dto.matrix.MatrixPointDto;
import mmojzis.zp.slovak_tsp.controller.mapper.SlovakMapper;
import mmojzis.zp.slovak_tsp.domain.*;
import mmojzis.zp.slovak_tsp.domain.entity.MapPoint;
import mmojzis.zp.slovak_tsp.service.request.SlovakTSPRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class MatrixTSPServiceTest {

    private static final List<TSPSelector> SELECTORS = Stream.of(TSPSelector.TOURNAMENT, null).toList();

    private static final List<String> SELECTOR_MODIFIERS = Stream.of("3", null).toList();

    private static final List<Integer> SELECTOR_ELITES = Stream.of(5, null).toList();

    private static final TSPMutator MUTATOR = TSPMutator.INVERSION;

    private static final String MUTATOR_MODIFIER = "0.5";

    private static final TSPCrossover CROSSOVER = TSPCrossover.CX;

    private static final String CROSSOVER_MODIFIER = "0.5";

    private static final Double OFFSPRING_FRACTION = 0.5;

    @Autowired
    private MatrixTSPService matrixTSPService;

    @Autowired
    private MapFeatureService featureService;

    @Autowired
    private SlovakMapper slovakMapper;

    @Test
    void serviceCanComputePrimitiveTSP() {
        ISeq<MatrixPoint> points = getRandomMatrixPoints(featureService.getPoints(), 2);
        SlovakResult result = matrixTSPService.calculate(getRequest(points, 5, 50L));
        assertThat(result).isNotNull();
        assertThat(result.getLength()).isPositive();
        assertThat(result.getRoute()).hasSize(points.length());
    }

    @Test
    void serviceCanComputeSimpleTSP() {
        for (int i = 0; i < 10; i++) {
            ISeq<MatrixPoint> points = getRandomMatrixPoints(featureService.getPoints(), 10);
            SlovakResult result = matrixTSPService.calculate(getRequest(points, 50, 5000L));
            assertThat(result).isNotNull();
            assertThat(result.getLength()).isPositive();
            assertThat(result.getRoute()).hasSize(points.length());
        }
    }

    @Test
    void serviceCanComputeMediumTSP() {
        for (int i = 0; i < 5; i++) {
            ISeq<MatrixPoint> points = getRandomMatrixPoints(featureService.getPoints(), 100);
            SlovakResult result = matrixTSPService.calculate(getRequest(points, 50, 7500L));
            assertThat(result).isNotNull();
            assertThat(result.getLength()).isPositive();
            assertThat(result.getRoute()).hasSize(points.length());
        }
    }

    @Test
    void serviceCanComputeComplexTSP() {
        for (int i = 0; i < 2; i++) {
            ISeq<MatrixPoint> points = getRandomMatrixPoints(featureService.getPoints(), 200);
            SlovakResult result = matrixTSPService.calculate(getRequest(points, 100, 10000L));
            assertThat(result).isNotNull();
            assertThat(result.getLength()).isPositive();
            assertThat(result.getRoute()).hasSize(points.length());
        }
    }

    private ISeq<MatrixPoint> getRandomMatrixPoints(List<MapPoint> points, int numberOfElements) {
        return slovakMapper.pointsToISeq(getRandomUniqueElements(points, numberOfElements)
                .stream()
                .map(mapPoint -> new MatrixPointDto(mapPoint.getCity()))
                .toList());
    }

    private static SlovakTSPRequest getRequest(ISeq<MatrixPoint> points, Integer popSize, Long maxGen) {
        SlovakTSPRequest request = new SlovakTSPRequest();
        request.setPoints(points);

        request.setCrossover(CROSSOVER);
        request.setCrossoverModifier(CROSSOVER_MODIFIER);
        request.setMutator(MUTATOR);
        request.setMutationModifier(MUTATOR_MODIFIER);
        request.setSelectors(SELECTORS);
        request.setNumberOfElites(SELECTOR_ELITES);
        request.setSelectionModifiers(SELECTOR_MODIFIERS);

        request.setPopulationSize(popSize);
        request.setOffspringFraction(OFFSPRING_FRACTION);

        request.setTermination(new TerminationConditions(maxGen));
        return request;
    }

}
