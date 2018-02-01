package hht.dragon.server;

import hht.dragon.server.request.SimpleServletRequest;
import hht.dragon.server.request.SimpleServletRequestImpl;
import hht.dragon.server.request.SimpleServletRequestImpl2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器类.
 *
 * @author: huang
 * Date: 18-1-17
 */
public class Server2 {

    private ServerSocket server;
    private Socket client;

    public Server2(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        if (server != null) {
            while (true) {
                client = server.accept();
                OutputStream output = client.getOutputStream();
                InputStream input = client.getInputStream();
                BufferedInputStream buffer = new BufferedInputStream(input);
                SimpleServletRequest request = new SimpleServletRequestImpl(buffer);
                HttpResponse response = new HttpResponse();
                response.respon(output, request.getRequestURI());
                buffer.close();
                input.close();
                output.close();
                client.close();
            }
        } else {
            System.out.println("服务未开启....");
        }
    }

    public static void main(String[] args) throws IOException {
        Server2 server = new Server2(8080);
        server.start();
    }

}
