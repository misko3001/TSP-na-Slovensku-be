package mmojzis.zp.gen_alg.controller;

import mmojzis.zp.gen_alg.domain.TempTSPResult;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TSPWebsocketController {

    public static final String TSP_SUBSCRIPTION_URL =  "/tsp/active/";

    public static final String TSP_SUBSCRIPTION_ARG =  "{processId}";

    @SendTo(TSP_SUBSCRIPTION_URL + TSP_SUBSCRIPTION_ARG)
    public TempTSPResult subscribeToActiveProcess(@DestinationVariable String processId) {
        throw new UnsupportedOperationException("Data is propagated from evolution interceptor");
    }
}