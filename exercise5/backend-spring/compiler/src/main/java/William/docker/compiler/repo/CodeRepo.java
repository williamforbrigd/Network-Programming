package William.docker.compiler.repo;

import William.docker.compiler.model.CodeOutput;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class CodeRepo {
    public void writeToFile(String code, String writeToPath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(writeToPath));
        writer.write(code);
        writer.close();
    }
    public CodeOutput buildAndRunDocker(CodeOutput code) throws IOException {
        String path = "src/main/resources/main.cpp";
        writeToFile(code.toString(), path);
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("bash", "src/main/resources/docker-script.sh");
        //pb.redirectErrorStream(true); //to get the ouput of the stream

        StringBuilder out = new StringBuilder();

        try{
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
                out.append(line).append("\n");
            }
            p.waitFor();
        } catch(IOException e) {
            System.out.println("Could not start process " + e.getMessage());
        } catch(InterruptedException e) {
            System.out.println("The current thread could not wait for the process to terminate: " + e.getMessage());
        }
        return new CodeOutput(out.toString());
    }

    /*
    public static void main(String[] args) throws IOException {
        //String to = "src/main/resources/main.cpp";
        //new CodeRepo().writeToFile(from, to);
        System.out.println(new CodeRepo().buildAndRunDocker(new CodeOutput()));
    }
     */
}
