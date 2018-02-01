package hht.dragon.server.request;

import hht.dragon.server.params.ContentType;
import hht.dragon.server.params.HttpRequestType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * SimpleServletRequest的实现类.
 *
 * @author: huang
 * Date: 18-1-18
 */
public class SimpleServletRequestImpl implements SimpleServletRequest {

    private String uri;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private String requestType;
    private static final String BLANK = " ";
    private String context;
    private String paramsForGet;

    private int index;

    public SimpleServletRequestImpl(InputStream input) {
        init(input);
    }

    private void init(InputStream input) {
        context = getHttpContext(input);
        setRequestType(context);
        setUri(context);
        System.out.println(context);
        setHeaders(context);
        setParams();
    }

    private void setRequestType(String context) {
        for (HttpRequestType type : HttpRequestType.values()) {
            if (context.startsWith(type.getValue())) {
                this.requestType = type.getValue();
            }
        }
    }

    private void setUri(String context) {
        if (context.startsWith(this.requestType + BLANK)) {
            index = context.indexOf("HTTP/1.1");
            uri = context.substring(context.indexOf(this.requestType + BLANK) + this.requestType.length() + 2, index - 1);
            if (HttpRequestType.GET.equals(this.requestType) && uri.contains("?")) {
                paramsForGet = uri.substring(uri.indexOf("?") + 1);
                uri = uri.substring(0, uri.indexOf("?"));
            }
            this.context = context.substring(context.indexOf("Host: "));
        }
    }

    private void setHeaders(String context) {
        String[] ss = context.split("\n");
        String key = null, value = null;
        int index = 0;
        for (String s : ss) {
            if (s.contains(": ")) {
                index = s.indexOf(": ");
                key = s.substring(0, index);
                key = key.replace("-", "");
                value = s.substring(index + 2);
                headers.put(key, value);
            }
        }
    }

    private void setParams() {
        // Get
        if (HttpRequestType.GET.equals(this.requestType) && uri.contains("?")) {
            String[] ps = this.paramsForGet.split("&");
            for (String s : ps) {
                String[] kv = s.split("=");
                params.put(kv[0], kv[1]);
            }
        }
        // Post
        if (HttpRequestType.POST.equals(this.requestType)) {
            String contentType = headers.get("ContentType");
            System.out.println("leixing: " + contentType);
            for (ContentType type : ContentType.values()) {
                // TODO 多类型
                if (contentType.contains(type.getValue())) {
                    getParamsForPost(type);
                }
            }
        }
    }

    private Map<String, String> getParamsForPost(ContentType type) {
        Map<String, String> map = new HashMap<>();
        switch (type) {
            case FORM:
                // TODO FORM

            case XW:
                // TODO XW

            case BINARY:
                // TODO BINARY

            default: break;
        }
        return map;
    }

    /**
     * 获取http内容.
     */
    private String getHttpContext(InputStream input) {
        String httpContext = null;
        int len;
        int byteLen = 1024;
        byte[] bufferByte = new byte[byteLen];
        try {
            len = input.read(bufferByte);
            if (len > 0) {
                httpContext = new String(bufferByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpContext;
    }

    @Override
    public String getRequestURI() {
        return this.uri;
    }

    @Override
    public String getRequestType() {
        return this.requestType;
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String getParameter(String name) {
        return params.get(name);
    }

    @Override
    public Set<String> getParameterNames() {
        return params.keySet();
    }
}
