package mmojzis.zp.gen_alg.common;

import io.jenetics.*;
import io.jenetics.util.MSeq;

import java.util.random.RandomGenerator;

public class InversionMutator<G extends Gene<?, G>, C extends Comparable<? super C>>
        extends Mutator<G, C> {

    public InversionMutator(final double probability) {
        super(probability);
    }

    @Override
    protected MutatorResult<Chromosome<G>> mutate(final Chromosome<G> chromosome,
                                                  final double p,
                                                  final RandomGenerator random) {
        final MutatorResult<Chromosome<G>> result;

        int length = chromosome.length();
        if (length > 1) {
            // Generate starting and ending index for inverting
            int start = random.nextInt(0, length - 1);
            int end = random.nextInt(start + 1, length);

            // Invert selected range of genes
            final MSeq<G> genes = MSeq.of(chromosome);
            int mutations = 0;
            for (int i = end; i >= start; i--) {
                genes.set(i, chromosome.get(start + mutations));
                mutations++;
            }

            result = new MutatorResult<>(
                    chromosome.newInstance(genes.toISeq()),
                    mutations
            );
        } else {
            result = new MutatorResult<>(chromosome, 0);
        }

        return result;
    }
}
