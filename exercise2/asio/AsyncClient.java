package asio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncClient {
    private AsynchronousSocketChannel client;
    private InetSocketAddress address;
    private Future<Void> future;

    public AsyncClient() throws IOException, InterruptedException, ExecutionException {
        client = AsynchronousSocketChannel.open();
        address = new InetSocketAddress("localhost", 4999);
        future = client.connect(address);
        future.get();
    }

    public String sendMessage(String msg) throws InterruptedException, ExecutionException {
        byte[] byteMsg = new String(msg).getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteMsg);
        Future<Integer> writeResult = client.write(buffer);

        writeResult.get();
        buffer.flip();
        Future<Integer> readResult = client.read(buffer);

        readResult.get();
        String echo = new String(buffer.array()).trim();
        buffer.clear();
        return echo;
    }

    public void cleanUp() throws IOException{
        client.shutdownInput();
        client.shutdownOutput();
        client.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        AsyncClient client = new AsyncClient();
        Scanner sc = new Scanner(System.in);
        String in = "";
        while(!in.equalsIgnoreCase("EXIT") || !in.equals("") || in == null) {
            in = sc.nextLine();
            System.out.println(client.sendMessage(in));
        }
        sc.close();
        client.cleanUp();
    }
}
