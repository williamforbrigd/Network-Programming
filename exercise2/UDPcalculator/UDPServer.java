
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.InetAddress;

public class UDPServer {
  static final int PORT = 1250;
  private DatagramSocket socket;
  private byte[] buffer;

  public UDPServer() throws SocketException {
    socket = new DatagramSocket(PORT);
    buffer = new byte[512];
  }

  public void runServer() throws IOException {
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet);
    System.out.println("Client connected successfully");
    InetAddress address = packet.getAddress();
    int port = packet.getPort();
    buffer = "Enter in the format: \"1 + 2\". Press enter to exit.".getBytes();
    packet = new DatagramPacket(buffer, buffer.length, address, port);
    socket.send(packet);

    while (true) {
      buffer = new byte[512];
      packet = new DatagramPacket(buffer, buffer.length);
      socket.receive(packet);
      String received = byteToStr(buffer);
      System.out.println("Data received from client: " + received);
      if (received.equalsIgnoreCase("EXIT") || received.equals("") || received == null) {
        System.out.println("Exiting...");
        socket.close();
        return;
      }
      String[] nums = received.split(" ");
      if (nums.length != 3) {
        buffer = "Please enter in a valid format.".getBytes();
        packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
        continue;
      }
      long num1 = 0, num2 = 0;
      try {
        num1 = Long.parseLong(nums[0]);
        num2 = Long.parseLong(nums[2]);
      } catch (NumberFormatException e) {
        System.out.println("Could not convert from string to long: " + e.getMessage());
      }
      char operator = nums[1].charAt(0);
      String msg = "";
      if (operator == '+') {
        msg = num1 + " + " + num2 + " = " + (num1 + num2);
        buffer = msg.getBytes();
      } else if (operator == '-') {
        msg = num1 + " - " + num2 + " = " + (num1 - num2);
        buffer = msg.getBytes();
      }
      packet = new DatagramPacket(buffer, buffer.length, address, port);
      socket.send(packet);
    }
  }

  private static String byteToStr(byte[] buf) {
    if (buf == null)
      return null;
    String str = "";
    int i = 0;
    while (buf[i] != 0) {
      str += (char) buf[i];
      i++;
    }
    return str;
  }

  public static void main(String[] args) throws IOException {
    UDPServer server = new UDPServer();
    server.runServer();
  }
}
