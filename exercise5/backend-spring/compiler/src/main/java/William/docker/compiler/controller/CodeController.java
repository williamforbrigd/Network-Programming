package William.docker.compiler.controller;

import William.docker.compiler.model.CodeOutput;
import William.docker.compiler.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeController {
    @Autowired
    private CodeService service;

    @PostMapping("/docker")
    public CodeOutput postCalled(@RequestBody CodeOutput code) throws Exception {
        //System.out.println(service.buildAndRunDocker(code));
        return service.buildAndRunDocker(code);
    }
}
