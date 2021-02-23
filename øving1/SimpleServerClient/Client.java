package SimpleServerClient;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  static final int PORTNR = 1250;

  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);

    Socket connection = new Socket("localhost", PORTNR);
    System.out.println("Forbindelsen er opprettet.");

    // Åpner strømmer for å kommunisere
    InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
    BufferedReader reader = new BufferedReader(readConnection);
    PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);

    // Leser info fra tjeneren
    System.out.println(reader.readLine() + "\n");

    // Leser fra tjeneren
    System.out.println(reader.readLine());
    char operator = sc.next().charAt(0);
    writer.println(operator);
    while (operator == '+' || operator == '-') {
      // Tall 1
      System.out.println(reader.readLine());
      String num1 = sc.next();
      writer.println(num1);

      // Tall 2
      System.out.println(reader.readLine());
      String num2 = sc.next();
      writer.println(num2);

      // Leser svaret fra tjeneren
      System.out.println(reader.readLine() + "\n");

      System.out.println(reader.readLine());
      operator = sc.next().charAt(0);
      writer.println(operator);
    }

    connection.close();
    reader.close();
    writer.close();
    sc.close();
  }
}
