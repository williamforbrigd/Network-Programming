import java.net.MulticastSocket;
import java.net.*;
import java.io.IOException;

/**
 * Hva to compile with: javac -Xlint *.java
 */

public class MulticastReceiver extends Thread {
  protected MulticastSocket socket;
  protected byte[] buf = new byte[256];

  @Override
  public void run() {
    try {
      socket = new MulticastSocket(4446);
      InetAddress group = InetAddress.getByName("230.0.0.0");
      socket.joinGroup(group);
      while (true) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Message received: " + received);
        if (received.equalsIgnoreCase("EXIT") || received.equals("") || received.equals("")) {
          break;
        }
      }
      socket.leaveGroup(group);
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 3; i++) {
      System.out.println("Waiting for thread: " + i + 1);
      new MulticastReceiver().run();
    }
  }
}
