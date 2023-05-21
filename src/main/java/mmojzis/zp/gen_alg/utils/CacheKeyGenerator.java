package mmojzis.zp.gen_alg.utils;

import java.text.Collator;
import java.util.Locale;

public final class CacheKeyGenerator {

    public static final Collator slovakCollator = Collator.getInstance(new Locale("sk", "SK"));

    private CacheKeyGenerator() {
    }

    public static String generateDistanceKey(String key1, String key2) {
        if (slovakCollator.compare(key1, key2) <= 0) {
            return key1 + "_" + key2;
        } else {
            return key2 + "_" + key1;
        }
    }
}
