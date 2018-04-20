package hht.dragon.server.tomcat.Processor;

import hht.dragon.server.tomcat.request.Request;
import hht.dragon.server.tomcat.response.Response;

import java.io.*;

/**
 * 静态文件处理.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class StaticResourceProcessor implements HttpProcessor {


    @Override
    public void process(Request request, Response response) {
        String uri = request.getUri();
        response.senStaticResource(uri);
    }
}
