package hht.dragon.server.tomcat;

import hht.dragon.server.tomcat.Processor.ServletProcessor;
import hht.dragon.server.tomcat.Processor.StaticResourceProcessor;
import hht.dragon.server.tomcat.request.Request;
import hht.dragon.server.tomcat.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * tomcat的启动类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class HttpServer {

    private static final String SHUTDOWN_COMMAN = "/SHUTDOWN";
    private boolean shutdown = false;

    /**
     * 服务主要运行方法.
     */
    public void run() {
        ServerSocket server = null;
        int port = 8080;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!shutdown) {
            InputStream input = null;
            OutputStream output = null;
            Socket client = null;
            try {
                client = server.accept();
                input = client.getInputStream();
                output = client.getOutputStream();

                Request request = new Request(input);
                request.init();

                if (request.getUri() == null) continue;

                Response response = new Response(output);
                response.setRequest(request);

                if (request.getUri().startsWith("/servlet/")) {
                    ServletProcessor processor = new ServletProcessor();
                    processor.process(request, response);
                    continue;
                }
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                    output.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.run();
    }

}
