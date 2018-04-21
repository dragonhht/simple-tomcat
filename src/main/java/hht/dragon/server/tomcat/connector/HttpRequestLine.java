package hht.dragon.server.tomcat.connector;

/**
 * Http请求行.
 *
 * @author: huang
 * Date: 2018/4/21
 */
final class HttpRequestLine {

    /** 初始请求方法数组大小. */
    public static final int INITIAL_METHOD_SIZE = 8;
    /** 初始uri数组大小. */
    public static final int INITIAL_URI_SIZE = 64;
    /** 初始协议数组大小. */
    public static final int INITIAL_PROTOCOL_SIZE = 8;
    /** 最大方法数组大小. */
    public static final int MAX_METHOD_SIZE = 1024;
    /** 最大uri数组大小. */
    public static final int MAX_URI_SIZE = 32768;
    /** 最大协议数组大小. */
    public static final int MAX_PROTOCOL_SIZE = 1024;

    public char[] method;
    public int methodEnd;
    public char[] uri;
    public int uriEnd;
    public char[] protocol;
    public int protocolEnd;

    public HttpRequestLine() {
        this(new char[INITIAL_METHOD_SIZE], 0,
                new char[INITIAL_URI_SIZE], 0,
                new char[INITIAL_PROTOCOL_SIZE], 0);
    }

    public HttpRequestLine(char[] method, int methodEnd, char[] uri,
                           int uriEnd, char[] protocol, int protocolEnd) {
        this.method = method;
        this.methodEnd = methodEnd;
        this.uri = uri;
        this.uriEnd = uriEnd;
        this.protocol = protocol;
        this.protocolEnd = protocolEnd;
    }

    /**
     * 释放所有引用，并初始化变量.
     */
    public void recycle() {
        this.methodEnd = 0;
        this.uriEnd = 0;
        this.protocolEnd = 0;
    }

}
