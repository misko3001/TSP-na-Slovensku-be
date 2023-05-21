package mmojzis.zp.gen_alg.service.request;

import lombok.Data;
import mmojzis.zp.gen_alg.domain.TSPCrossover;
import mmojzis.zp.gen_alg.domain.TSPMutator;
import mmojzis.zp.gen_alg.domain.TSPSelector;
import mmojzis.zp.gen_alg.domain.TerminationConditions;

import java.util.List;

@Data
public abstract class TSPRequest {

    private Integer populationSize;

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
