package com.github.dragonhht.http.request;

/**
 * Http请求行.
 *
 * @author: huang
 * Date: 2018/4/21
 */
final class HttpRequestLine {

    /** 初始请求方法数组大小. */
    static final int INITIAL_METHOD_SIZE = 8;
    /** 初始uri数组大小. */
    static final int INITIAL_URI_SIZE = 64;
    /** 初始协议数组大小. */
    static final int INITIAL_PROTOCOL_SIZE = 8;
    /** 最大方法数组大小. */
    static final int MAX_METHOD_SIZE = 1024;
    /** 最大uri数组大小. */
    static final int MAX_URI_SIZE = 32768;
    /** 最大协议数组大小. */
    static final int MAX_PROTOCOL_SIZE = 1024;

    char[] method;
    int methodEnd;
    char[] uri;
    int uriEnd;
    char[] protocol;
    int protocolEnd;

    HttpRequestLine() {
        this(new char[INITIAL_METHOD_SIZE], 0,
                new char[INITIAL_URI_SIZE], 0,
                new char[INITIAL_PROTOCOL_SIZE], 0);
    }

    HttpRequestLine(char[] method, int methodEnd, char[] uri,
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
    void recycle() {
        this.methodEnd = 0;
        this.uriEnd = 0;
        this.protocolEnd = 0;
    }

    /**
     * 获取指定字符串在uri中的位置.
     * @param buf 指定字符
     * @param end 需查询的字符长度
     * @return 位置
     */
    public int indexOf(char[] buf, int end) {
        char firstChar = buf[0];
        int pos = 0;
        while (pos < uriEnd) {
            pos = indexOf(firstChar, pos);
            if (pos == -1) return -1;
            if ((uriEnd - pos) < end) return -1;
            for (int i = 0; i < end; i++) {
                if (uri[i + pos] != buf[i]) break;
                if (i == (end -1)) return pos;
            }
            pos++;
        }
        return -1;
    }

    public int indexOf(char[] buf) {
        return indexOf(buf, buf.length);
    }

    public int indexOf(String str) {
        return indexOf(str.toCharArray(), str.length());
    }

    /**
     * 获取指定字符在uri中的位置.
     * @param c 需查找到的字符
     * @param start 开始位置
     * @return 位置
     */
    public int indexOf(char c, int start) {
        for (int i = start; i < uriEnd; i++) {
            if (uri[i] == c) {
                return i;
            }
        }
        return -1;
    }

    String getMethod() {
        return new String(this.method, 0, this.methodEnd);
    }

    String getUri(int end) {
        return new String(this.uri, 0, end);
    }

    String getProtocol() {
        return new String(this.protocol, 0, this.protocolEnd);
    }

}
