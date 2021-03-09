package William.docker.compiler.controller;

import William.docker.compiler.model.CppProgram;
import William.docker.compiler.service.CppProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CppProgramController {
    @Autowired
    private CppProgramService service;

    @PostMapping("/docker")
    public CppProgram postCalled(@RequestBody CppProgram cppProgram) throws Exception {
        return service.buildAndRunDocker(cppProgram);
    }
}
