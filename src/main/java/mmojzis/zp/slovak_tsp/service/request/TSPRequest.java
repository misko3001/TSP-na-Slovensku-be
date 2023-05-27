package mmojzis.zp.slovak_tsp.service.request;

import lombok.Data;
import mmojzis.zp.slovak_tsp.domain.TSPCrossover;
import mmojzis.zp.slovak_tsp.domain.TSPMutator;
import mmojzis.zp.slovak_tsp.domain.TSPSelector;
import mmojzis.zp.slovak_tsp.domain.TerminationConditions;

import java.util.List;

@Data
public abstract class TSPRequest {

    private Integer populationSize;

    private Long maxPhenotypeAge;

    private Double offspringFraction;

    private List<TSPSelector> selectors;

    private List<String> selectionModifiers;

    private List<Integer> numberOfElites;

    private TSPMutator mutator;

    private String mutationModifier;

    private TSPCrossover crossover;

    private String crossoverModifier;

    private TerminationConditions termination;

    private String processId;

    private Long publishEachGeneration;

    public boolean hasInterceptor() {
        return processId != null && publishEachGeneration != null;
    }
}
