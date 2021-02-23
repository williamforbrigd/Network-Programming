import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MulticastPublisher {
  private DatagramSocket socket;
  private InetAddress group;
  private byte[] buf;

  public void multicast(String message) throws IOException {
    socket = new DatagramSocket();
    group = InetAddress.getByName("230.0.0.0");
    buf = message.getBytes();

    DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
    socket.send(packet);
    socket.close();
  }

  public static void main(String[] args) throws IOException {
    MulticastPublisher publisher = new MulticastPublisher();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String line = reader.readLine();
    while (true) {
      if (line == null || line.equalsIgnoreCase("EXIT") || line.equals("")) {
        publisher.multicast(line);
        break;
      }
      publisher.multicast(line);
      line = reader.readLine();
    }
  }
}
