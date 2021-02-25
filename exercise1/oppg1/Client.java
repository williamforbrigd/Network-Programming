package oppg1;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {    
    static final int PORTNR = 1250;
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader reader = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        
        try {
            socket = new Socket("localhost", 1250);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }

        String recieved;
        String toReturn;
        while(true) {
            try {
                recieved = in.readUTF();
                System.out.println(recieved);

                //Les hvilken operator som skal brukes. Skriv EXIT eller bruk return hvis avslutte. 
                toReturn = reader.readLine();
                out.writeUTF(toReturn);
                if(toReturn.equalsIgnoreCase("EXIT") || toReturn == null || toReturn.equals("")) {
                    socket.close();
                    return;
                }

                //Tall 1
                recieved = in.readUTF();
                System.out.println(recieved);
                toReturn = reader.readLine();
                out.writeUTF(toReturn);

                //Tall 2
                recieved = in.readUTF();
                System.out.println(recieved);
                toReturn = reader.readLine();
                out.writeUTF(toReturn);
                
                //Svaret
                System.out.println("Mottar utregning fra tjener: " + in.readUTF() + "\n");

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
