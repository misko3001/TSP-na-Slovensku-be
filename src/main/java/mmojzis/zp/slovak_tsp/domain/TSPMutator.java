package mmojzis.zp.slovak_tsp.domain;

import io.jenetics.SwapMutator;
import lombok.Getter;
import mmojzis.zp.slovak_tsp.common.InversionMutator;

@Getter
public enum TSPMutator {
    SWAP(SwapMutator.class),
    INVERSION(InversionMutator.class);

    TSPMutator(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Class<?> clazz;
}
