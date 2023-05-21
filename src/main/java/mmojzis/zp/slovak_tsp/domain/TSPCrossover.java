package mmojzis.zp.slovak_tsp.domain;

import io.jenetics.PartiallyMatchedCrossover;
import lombok.Getter;
import mmojzis.zp.slovak_tsp.common.CycleCrossover;

@Getter
public enum TSPCrossover {
    PMX(PartiallyMatchedCrossover.class),
    CX(CycleCrossover.class);

    TSPCrossover(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Class<?> clazz;
}
