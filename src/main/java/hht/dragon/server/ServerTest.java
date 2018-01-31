package hht.dragon.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 使用通道.
 *
 * @author: huang
 * Date: 18-1-31
 */
public class ServerTest {

    private ServerSocketChannel server;

    public ServerTest(int port) {
        try {
            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        SocketChannel socket = server.accept();
        socket.read(buffer);
        byte[] bytes = new byte[1024];

    }

    public static void main(String[] args) throws IOException {
        ServerTest test = new ServerTest(8080);
        test.start();
    }

}
