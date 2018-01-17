package hht.dragon.server;

import java.io.IOException;
import java.io.InputStream;

/**
 * http请求处理.
 *
 * @author: huang
 * Date: 18-1-17
 */
public class HttpRequest {

    private String uri;

    public HttpRequest() {

    }

    public void getRequest(InputStream input) {
        int len;
        int byteLen = 1024;
        byte[] bufferByte = new byte[byteLen];
        try {
            len = input.read(bufferByte);
            if (len > 0) {
                String httpContext = new String(bufferByte);
                uri = httpContext.substring(httpContext.indexOf("GET ") + 5, httpContext.indexOf(" HTTP/1.1"));
                System.out.println(uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUri() {
        return uri;
    }
}
