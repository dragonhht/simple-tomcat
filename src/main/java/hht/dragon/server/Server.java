package hht.dragon.server;

import hht.dragon.server.request.SimpleServletRequest;
import hht.dragon.server.request.SimpleServletRequestImpl;
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
public class Server {

    private ServerSocket server;
    private Socket client;

    public Server(int port) {
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
                InputStream input = client.getInputStream();
                BufferedInputStream buffer = new BufferedInputStream(input);
                OutputStream output = client.getOutputStream();
                SimpleServletRequest request = new SimpleServletRequestImpl(input);
                HttpResponse response = new HttpResponse();
                for (String key : request.getParameterNames()) {
                    System.out.println(key + " : " + request.getParameter(key));
                }
                System.out.println(request.getHeader(""));
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
        Server server = new Server(8080);
        server.start();
    }

}
