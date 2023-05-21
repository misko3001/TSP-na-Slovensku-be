package mmojzis.zp.slovak_tsp.utils.factory;

import io.jenetics.Gene;
import io.jenetics.engine.Problem;
import mmojzis.zp.slovak_tsp.common.EvolutionWebSocketInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class InterceptorBeanFactory {

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T, G extends Gene<?, G>, C extends Comparable<? super C>> EvolutionWebSocketInterceptor<T, G, C> createInterceptor(
            Problem<T, G, C> task,
            String processId,
            Long publishEachGeneration) {
        EvolutionWebSocketInterceptor<T, G, C> interceptor = applicationContext.getBean(EvolutionWebSocketInterceptor.class);
        interceptor.setTask(task);
        interceptor.setProcessId(processId);
        interceptor.setPublishEachGeneration(publishEachGeneration);
        return interceptor;
    }
}
