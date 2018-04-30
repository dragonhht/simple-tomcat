package hht.dragon.server.tomcat.Processor;

import hht.dragon.server.tomcat.connector.Request;
import hht.dragon.server.tomcat.connector.Response;

/**
 * 请求处理接口.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public interface HttpDoProcessor {

    void process(Request request, Response response);

}
