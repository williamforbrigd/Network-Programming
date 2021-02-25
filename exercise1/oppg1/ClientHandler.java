package oppg1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket socket;
    private int id;

    public ClientHandler(Socket socket, DataInputStream in, DataOutputStream out, int id) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public void run() {
        String recieved;
        char operator;
        while(true) {
            try {
                //Send melding til klienten
                out.writeUTF("+ eller -");

                recieved = in.readUTF();
                if(recieved.equalsIgnoreCase("EXIT") || recieved == null || recieved.equals("")) {
                    socket.close();
                    return;
                }
                operator = recieved.charAt(0);
                System.out.println("Operatoren er: " + operator);

                out.writeUTF("Tall 1: ");
                int num1 = Integer.parseInt(in.readUTF());
                System.out.println("Tall 1 leste fra klient: " + num1);

                out.writeUTF("Tall 2: ");
                int num2 = Integer.parseInt(in.readUTF());
                System.out.println("Tall 2 leste fra klient: " + num2);

                Calculator c = new Calculator(operator, num1, num2);
                c.calculate();
                out.writeUTF(c.toString());
                
            } catch(IOException e) {
                e.printStackTrace();
            } catch(NumberFormatException e) {
                System.out.println("Kunne inne gjøre om fra string til int: " + e.getMessage());
            }
        }
    }
}
