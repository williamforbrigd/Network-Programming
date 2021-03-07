package William.docker.compiler.service;

import William.docker.compiler.model.CodeOutput;
import William.docker.compiler.repo.CodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CodeService {
    @Autowired
    private CodeRepo repo;

    public CodeOutput buildAndRunDocker(CodeOutput code) throws IOException {
        return repo.buildAndRunDocker(code);
    }
}
