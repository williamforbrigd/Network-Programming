package oppg2;

import java.net.Socket;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WebClient {
  static final int PORTNR = 80;

  public static void main(String[] args) {
    Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    try {
      socket = new Socket("localhost", 80);
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());

      for (int i = 0; i < 10; i++) {
        out.writeBytes(in.readUTF());
      }

      socket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
