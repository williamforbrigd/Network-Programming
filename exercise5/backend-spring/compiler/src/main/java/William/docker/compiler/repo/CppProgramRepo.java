package William.docker.compiler.repo;

import William.docker.compiler.model.CppProgram;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class CppProgramRepo {
    public void writeToFile(String code, String writeToPath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(writeToPath));
        writer.write(code);
        writer.close();
    }

    public CppProgram buildAndRunDocker(CppProgram cppProgram) throws IOException {
        String path = "src/main/resources/main.cpp";
        writeToFile(cppProgram.toString(), path);
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("bash", "src/main/resources/docker-script.sh");
        StringBuilder out = new StringBuilder();

        try{
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
                out.append(line).append("\n");
            }
            if(line == null && out.toString().equals("")) {
                out.append("Error: could not compile the program");
            }
            p.waitFor();
        } catch(IOException e) {
            System.out.println("Could not start process " + e.getMessage());
        } catch(InterruptedException e) {
            System.out.println("The current thread could not wait for the process to terminate: " + e.getMessage());
        }
        return new CppProgram(out.toString());
    }
}
