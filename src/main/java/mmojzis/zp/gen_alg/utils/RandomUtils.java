package mmojzis.zp.gen_alg.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {

    private RandomUtils() {
    }

    /**
     * Durstenfeld's algorithm
     * @return A sublist of n random unique elements from the provided list or empty list
     * if list.size() < n
     */
    public static <E> List<E> getRandomUniqueElements(List<E> list, int n, Random r) {
        int length = list.size();
        if (length >= n) {
            for (int i = length - 1; i >= length - n; --i) {
                Collections.swap(list, i , r.nextInt(i + 1));
            }
            return list.subList(length - n, length);
        }
        return Collections.emptyList();

    }

    public static <E> List<E> getRandomUniqueElements(List<E> list, int n) {
        return getRandomUniqueElements(list, n, ThreadLocalRandom.current());
    }
}
