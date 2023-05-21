package mmojzis.zp.gen_alg.common;

import io.jenetics.Crossover;
import io.jenetics.EnumGene;
import io.jenetics.util.MSeq;
import io.jenetics.util.RandomRegistry;

import static java.lang.String.format;

public class CycleCrossover<T, C extends Comparable<? super C>>
        extends Crossover<EnumGene<T>, C> {

    public CycleCrossover(double probability) {
        super(probability);
    }

    @Override
    protected int crossover(MSeq<EnumGene<T>> that, MSeq<EnumGene<T>> other) {
        int length = that.length();
        if (length != other.length()) {
            throw new IllegalArgumentException(format(
                    "Required chromosomes with same length: %s != %s",
                    that.length(), other.length()
            ));
        }
        int start = RandomRegistry.random().nextInt(0, length);
        boolean[] dontSwap = cycleCrossover(start, length, that, other);
        int altered = 0;
        for (int i = 0; i < length; i++) {
            if (!dontSwap[i]) {
                swap(i, that, other);
                altered++;
            }
        }
        return altered;
    }

    @Override
    public String toString() {
        return format("%s[p=%f]", getClass().getSimpleName(), _probability);
    }

    private void swap(Integer index, MSeq<EnumGene<T>> parent1, MSeq<EnumGene<T>> parent2) {
        // Possibly only swap if alleles are not equal
        EnumGene<T> temp = parent1.get(index);
        parent1.set(index, parent2.get(index));
        parent2.set(index, temp);
    }

    /**
     * @return Boolean array which corresponds to indices of both parents and if value at index is false
     * the genes at that index should be swapped
     */
    private boolean[] cycleCrossover(int start, int length, MSeq<EnumGene<T>> parent1, MSeq<EnumGene<T>> parent2) {
        // True - no swap || False - do swap
        boolean[] dontSwap = new boolean[length];

        // Setup variables
        MSeq<EnumGene<T>> cycleGenes = MSeq.ofLength(length);
        cycleGenes.set(start, parent1.get(start));

        // Select starting point
        EnumGene<T> correspondingGene = parent2.get(start);
        dontSwap[start] = true;

        // Find cycle in the first offspring
        int index;
        while (!cycleGenes.contains(correspondingGene)) {
            index = parent1.indexOf(correspondingGene);
            cycleGenes.set(index, parent1.get(index));
            dontSwap[index] = true;
            correspondingGene = parent2.get(index);
        }
        return dontSwap;
    }
}
