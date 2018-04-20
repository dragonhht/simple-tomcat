package hht.dragon.server.tomcat.Processor;

import hht.dragon.server.tomcat.request.Request;
import hht.dragon.server.tomcat.response.Response;

/**
 * servlet处理.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class ServletProcessor implements HttpProcessor {
    @Override
    public void process(Request request, Response response) {
        System.out.println("处理servlet");
    }
}
