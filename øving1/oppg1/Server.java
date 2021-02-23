package oppg1;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Server {
    static final int PORTNR = 1250;
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        
        try {
            serverSocket = new ServerSocket(PORTNR);
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Hvor mange threads?");
        int n=0;
        try {
            n = Integer.parseInt(new java.util.Scanner(System.in).nextLine());
        } catch(NumberFormatException e) {
            System.out.println("Skriv inn et tall: " + e.getMessage());
        }

        int id=0;
        for(int i=0; i < n; i++) {
            try {
		System.out.println("Venter på forbindelse..");
                socket = serverSocket.accept();

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                Thread t = new ClientHandler(socket, in, out, ++id);
                System.out.println("Hello from thread: " + t.getId());
                t.run();
                System.out.print("\n");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
