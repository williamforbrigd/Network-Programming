package asio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.concurrent.Future;

public class AsyncServer {
    private AsynchronousServerSocketChannel serverChannel;
    private Future<AsynchronousSocketChannel> acceptResult;
    private AsynchronousSocketChannel clientChannel;
    private InetSocketAddress address;

    public AsyncServer(String address, int port) {
        this.address = new InetSocketAddress(address, port);
    }

    public void runServer() throws Exception, IOException {
        serverChannel = AsynchronousServerSocketChannel.open();
        serverChannel.bind(this.address);

        //Finishing the program after 10 clients.
        for(int i=0; i < 10; i++) {
            acceptResult = serverChannel.accept();
            System.out.println("Waiting for client..");

            clientChannel = acceptResult.get();
            System.out.println("Client local address: " + clientChannel.getLocalAddress() + "\n");

            new Thread(new AsyncServerThread(clientChannel)).start();
        }
    }

    public static void main(String[] args) throws IOException, Exception {
        AsyncServer server = new AsyncServer("localhost", 4999);
        server.runServer();
    }

}