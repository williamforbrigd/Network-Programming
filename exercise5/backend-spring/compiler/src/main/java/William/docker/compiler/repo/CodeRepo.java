package William.docker.compiler.repo;

import William.docker.compiler.model.CodeOutput;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class CodeRepo {
    public void writeToFile(String from, String to) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(from));
        BufferedWriter writer = new BufferedWriter(new FileWriter(to ));
        String line = "";
        while((line = reader.readLine()) != null) {
            writer.write(line + "\n");
        }
        reader.close();
        writer.close();
    }
    public CodeOutput buildAndRunDocker() throws IOException {
        String from = "src/main/resources/HelloWorld.cpp";
        String to = "src/main/resources/main.cpp";
        writeToFile(from, to);
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
            return new CodeOutput(out);
        } catch(IOException e) {
            System.out.println("Could not start process " + e.getMessage());
        } catch(InterruptedException e) {
            System.out.println("The current thread could not wait for the process to terminate: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String from = "src/main/resources/HelloWorld.cpp";
        String to = "src/main/resources/main.cpp";
        new CodeRepo().writeToFile(from, to);
    }
}
