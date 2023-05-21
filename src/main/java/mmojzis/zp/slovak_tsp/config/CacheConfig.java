package mmojzis.zp.slovak_tsp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class CacheConfig {

    @Bean
    public ConcurrentMap<String, Double> distanceCache() {
        return new ConcurrentHashMap<>(19900);
    }
}
