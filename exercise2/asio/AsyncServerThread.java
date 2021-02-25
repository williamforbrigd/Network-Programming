package asio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncServerThread implements Runnable {
    private AsynchronousSocketChannel clientChannel;

    public AsyncServerThread(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public void run() {
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(32);
            Future<Integer> readResult = clientChannel.read(buffer);

            try {
                readResult.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            buffer.flip();
            String message = new String(buffer.array()).trim();
            if (message.equalsIgnoreCase("EXIT") || message == null || message.equals("")) {
                break;
            }

            buffer = ByteBuffer.wrap(new String(message).getBytes());
            Future<Integer> writeResult = clientChannel.write(buffer);

            try {
                writeResult.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            buffer.clear();
        }
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
