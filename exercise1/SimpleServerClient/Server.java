package SimpleServerClient;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server {
  static final int PORTNR = 1250;

  public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(PORTNR);
    System.out.println("Venter på forbindelse..");
    Socket connection = server.accept(); // venter til noen tar kontakt

    // Opens the stream to communicate with a client
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);

    writer.println("Info: for at programmet skal avsluttes, skriv en annen bokstav enn + eller -");

    // Sender melding til klienten
    writer.println("+ eller -");
    char operator = reader.readLine().charAt(0);
    while (operator == '+' || operator == '-') {
      System.out.println("Operatoren er: " + operator);

      writer.println("Skriv tall 1");
      int num1 = Integer.parseInt(reader.readLine());
      System.out.println("Tall 1: " + num1);

      writer.println("Skriv tall 2");
      int num2 = Integer.parseInt(reader.readLine());
      System.out.println("Tall 2: " + num2);

      int res = -1;
      if (operator == '+') {
        res = num1 + num2;
      } else if (operator == '-') {
        res = num1 - num2;
      }
      if (res != -1)
        writer.println(num1 + " " + operator + " " + num2 + " = " + res);
      System.out.print("\n");

      // Sender melding til klienten
      writer.println("+ eller -");
      operator = reader.readLine().charAt(0);
    }
    server.close();
    connection.close();
    reader.close();
    writer.close();
  }
}
