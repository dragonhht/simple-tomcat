package hht.dragon.server;

import hht.dragon.server.request.SimpleServletRequest;
import hht.dragon.server.request.SimpleServletRequestImpl;
import hht.dragon.server.request.SimpleServletRequestImpl2;

import java.io.*;
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
                OutputStream output = client.getOutputStream();
/*                BufferedInputStream buffer = new BufferedInputStream(input);
                SimpleServletRequest request = new SimpleServletRequestImpl(input);*/
                SimpleServletRequest request = new SimpleServletRequestImpl2(client);
                HttpResponse response = new HttpResponse();
                for (String key : request.getParameterNames()) {
                    System.out.println(key + " : " + request.getParameter(key));
                }
                response.respon(output, request.getRequestURI());
//                buffer.close();
                output.close();
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
