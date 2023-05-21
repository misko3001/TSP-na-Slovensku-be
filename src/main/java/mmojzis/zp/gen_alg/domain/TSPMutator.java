package mmojzis.zp.gen_alg.domain;

import io.jenetics.SwapMutator;
import lombok.Getter;
import mmojzis.zp.gen_alg.common.InversionMutator;

@Getter
public enum TSPMutator {
    SWAP(SwapMutator.class),
    INVERSION(InversionMutator.class);

    TSPMutator(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Class<?> clazz;
}
