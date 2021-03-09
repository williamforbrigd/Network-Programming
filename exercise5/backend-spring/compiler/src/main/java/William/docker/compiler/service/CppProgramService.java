package William.docker.compiler.service;

import William.docker.compiler.model.CppProgram;
import William.docker.compiler.repo.CppProgramRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CppProgramService {
    @Autowired
    private CppProgramRepo repo;

    public CppProgram buildAndRunDocker(CppProgram cppProgram) throws IOException {
        return repo.buildAndRunDocker(cppProgram);
    }
}
