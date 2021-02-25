package oppg2;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class WebServer {
  static final int PORT = 80;

  public static void main(String[] args) {
    ServerSocket server = null;
    Socket connection = null;

    InputStreamReader readConnection = null;
    BufferedReader reader = null;
    PrintWriter writer = null;

    try {
      server = new ServerSocket(PORT);
      System.out.println("Waiting for connection...");
      connection = server.accept();
      readConnection = new InputStreamReader(connection.getInputStream());
      reader = new BufferedReader(readConnection);

      writer = new PrintWriter(connection.getOutputStream(), true);

      String header = "";
      String line;
      while (!(line = reader.readLine()).equals("")) {
        header += "<li>" + line + "</li>";
      }

      writer.println("HTTP/1.0 200 OK");
      writer.println("Content-Type: text/html; charset=utf-8");
      writer.println("");
      writer.println("<!DOCTYOE html>");
      writer.println("<html>");
      writer.println("<body>");
      writer.println("<h1>Welcome!</h1>");
      writer.println("<ul>");
      writer.println(header);
      writer.println("</ul>");
      writer.println("</body>");
      writer.println("</html>");

      writer.flush();
      server.close();
      connection.close();
      reader.close();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
