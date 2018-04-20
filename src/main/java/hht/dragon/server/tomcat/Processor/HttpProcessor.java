package hht.dragon.server.tomcat.Processor;

import hht.dragon.server.tomcat.request.Request;
import hht.dragon.server.tomcat.response.Response;

/**
 * 请求处理接口.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public interface HttpProcessor {

    void process(Request request, Response response);

}
