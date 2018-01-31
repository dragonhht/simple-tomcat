package hht.dragon.server.request;

import hht.dragon.server.params.HttpRequestType;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description.
 *
 * @author: huang
 * Date: 18-1-25
 */
public class SimpleServletRequestImpl2 implements SimpleServletRequest {

    private String uri;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private String requestType;
    private static final String BLANK = " ";
    private String context;
    private String paramsForGet;

    public SimpleServletRequestImpl2(Socket socket) {
        getHttpContext(socket);
    }

    private void getHttpContext(Socket socket) {
        String s;
        int index = 0;
        InputStream input = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            input = socket.getInputStream();
            reader = new InputStreamReader(input);
            bufferedReader = new BufferedReader(reader);
            while ((s = bufferedReader.readLine()) != null) {
                System.out.println(s);
                if (index == 0) {
                    setRequestType(s);
                    setUri(s);
                }
                index++;
            }
            System.out.println("结束");
        } catch (IOException e) {
            System.out.println("异常");
            e.printStackTrace();
        }finally {
            if (socket != null) {
                try {
                    bufferedReader.close();
                    reader.close();
                    input.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setRequestType(String context) {
        for (HttpRequestType type : HttpRequestType.values()) {
            if (context.startsWith(type.getValue())) {
                this.requestType = type.getValue();
            }
        }
    }

    private void setUri(String context) {
        this.uri = context.substring(context.indexOf(this.requestType + BLANK) + this.requestType.length() + 2,
                context.indexOf("HTTP/1.1") - 1);
    }

    @Override
    public String getRequestURI() {
        return this.uri;
    }

    @Override
    public String getRequestType() {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public Set<String> getParameterNames() {
        return null;
    }
}
