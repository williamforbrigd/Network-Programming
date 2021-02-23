package oppg2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"));
        writer.write("hei");
        writer.close();
    }
    
}
