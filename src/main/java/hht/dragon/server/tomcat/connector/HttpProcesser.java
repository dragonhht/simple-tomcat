package hht.dragon.server.tomcat.connector;

import hht.dragon.server.tomcat.Processor.ServletDoProcessor;
import hht.dragon.server.tomcat.Processor.StaticResourceDoProcessor;
import hht.dragon.server.tomcat.request.Request;
import hht.dragon.server.tomcat.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 负责创建Request和Response实例.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class HttpProcesser {

    public void process(Socket socket) {
        InputStream input;
        OutputStream output;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            Request request = new Request(input);
            request.init();

            if (request.getUri() == null) return;

            Response response = new Response(output);
            response.setRequest(request);

            if (request.getUri().startsWith("/servlet/")) {
                ServletDoProcessor processor = new ServletDoProcessor();
                processor.process(request, response);
            } else {
                StaticResourceDoProcessor processor = new StaticResourceDoProcessor();
                processor.process(request, response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
