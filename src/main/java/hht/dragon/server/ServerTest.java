package hht.dragon.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 使用通道.
 *
 * @author: huang
 * Date: 18-1-31
 */
public class ServerTest {

    private ServerSocketChannel server;
    private Selector selector;

    public ServerTest(int port) {
        try {
            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(port));
            server.configureBlocking(false);
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                if (sk.isAcceptable()) {
                    SocketChannel socket = server.accept();
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    SocketChannel socket = (SocketChannel) sk.channel();
                    socket.read(buffer);
                    System.out.println(new String(buffer.array()));
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        ServerTest test = new ServerTest(8080);
        test.start();
    }

}
