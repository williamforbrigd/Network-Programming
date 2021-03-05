package William.docker.compiler.repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeRepo {
    public static void runCommand() {
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
                System.out.println(out);
            }
            p.waitFor();
        } catch(IOException e) {
            System.out.println("Could not start process " + e.getMessage());
        } catch(InterruptedException e) {
            System.out.println("The current thread could not wait for the process to terminate: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        runCommand();
    }
}
