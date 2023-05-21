package mmojzis.zp.gen_alg.domain;

import io.jenetics.PartiallyMatchedCrossover;
import lombok.Getter;
import mmojzis.zp.gen_alg.common.CycleCrossover;

@Getter
public enum TSPCrossover {
    PMX(PartiallyMatchedCrossover.class),
    CX(CycleCrossover.class);

    TSPCrossover(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Class<?> clazz;
}
