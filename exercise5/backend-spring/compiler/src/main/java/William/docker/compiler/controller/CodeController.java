package William.docker.compiler.controller;

import William.docker.compiler.model.CodeOutput;
import William.docker.compiler.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CodeController {
    @Autowired
    private CodeService service;

    @GetMapping("/docker")
    public StringBuilder buildAndRunDocker() throws IOException {
        return service.buildAndRunDocker().getCode();
    }
}
