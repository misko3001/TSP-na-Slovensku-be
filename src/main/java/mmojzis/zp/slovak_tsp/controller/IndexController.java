package mmojzis.zp.slovak_tsp.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    private final Resource index;

    public IndexController() {
        this.index = new ClassPathResource("/rcapp/index.html");
    }

    @GetMapping(value = { "", "/", "/index.html", "/error" })
    @ResponseBody
    public Resource index() {
        return index;
    }
}
