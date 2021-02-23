
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

public class UDPClient {
  static final int PORT = 1250;
  private DatagramSocket socket;
  private byte[] buffer = null;
  private InetAddress address;

  public UDPClient() throws SocketException, UnknownHostException {
    socket = new DatagramSocket();
    address = InetAddress.getLocalHost();
  }

  public void runClient() throws IOException {
    buffer = new byte[512];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
    socket.send(packet);

    buffer = new byte[512];
    packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet);
    System.out.println(byteToStr(buffer));

    Scanner sc = new Scanner(System.in);
    String line = "";
    while (true) {
      line = sc.nextLine();
      buffer = line.getBytes();
      packet = new DatagramPacket(buffer, buffer.length, address, PORT);
      socket.send(packet);
      if (line == null || line.equalsIgnoreCase("EXIT") || line.equals("")) {
        sc.close();
        socket.close();
        return;
      }
      buffer = new byte[512];
      packet = new DatagramPacket(buffer, buffer.length);
      socket.receive(packet);
      System.out.println(byteToStr(buffer));
    }
  }

  private static String byteToStr(byte[] buf) {
    String str = "";
    int i = 0;
    while (buf[i] != 0) {
      str += (char) buf[i];
      i++;
    }
    return str;
  }

  public static void main(String[] args) throws IOException {
    UDPClient client = new UDPClient();
    client.run();
  }
}
