package com.github.dragonhht.http.request;

import com.github.dragonhht.http.utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 解析并获取Request.
 *
 * @author: huang
 * @Date: 18-11-10
 */
public class RequestProcesser {

    private static final RequestProcesser util = new RequestProcesser();

    private RequestProcesser() {}

    public static RequestProcesser getInstance() {
        return util;
    }

    /**
     * 解析HTTP请求.
     * @param input Request的输入流.
     * @return 填充好的Request
     */
    public HttpServletRequest process(SocketInputStream input) throws IOException, ServletException {
        Request request = new Request(input);
        HttpRequestLine requestLine = new HttpRequestLine();
        // 解析http
        parseRequest(input, requestLine, request);
        // 解析请求信息
        parseHeaders(input, request);
        // 使用RequestFacade对象屏蔽多余属性与方法
        return new RequestFacade(request);
    }

    /**
     * 解析请求行.
     */
    private void parseRequest(SocketInputStream input, HttpRequestLine requestLine, Request request)
            throws IOException, ServletException {
        input.readRequestLine(requestLine);
        String method = requestLine.getMethod();
        String uri;
        String protocol = requestLine.getProtocol();

        // 校验请求行
        if (method.length() < 1) {
            throw new ServletException("Missing HTTP request method");
        }else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP request uri");
        }
        // 解析uri中的参数
        int queryIndex = requestLine.indexOf("?");
        if (queryIndex >= 0) {
            request.setQueryString(new String(requestLine.uri, queryIndex + 1,
                    requestLine.uriEnd - queryIndex - 1));
            uri = requestLine.getUri(queryIndex);
        } else {
            request.setQueryString(null);
            uri = requestLine.getUri(requestLine.uriEnd);
        }
        // 若uri包含协议与域名信息
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            if (pos != -1) {
                pos = uri.indexOf("/", pos + 3);
                if (pos == -1) {
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }

        // 解析uri中可能包含的session Id
        String match = ";jsessionid=";
        int matchIndex = uri.indexOf(match);
        if (matchIndex >= 0) {
            String rest = uri.substring(matchIndex + match.length());
            int matchIndex2 = rest.indexOf(';');
            if (matchIndex2 >= 0) {
                request.setRequestSessionId(rest.substring(0, matchIndex2));
                rest = rest.substring(matchIndex2);
            } else {
                request.setRequestSessionId(rest);
                rest = "";
            }
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, matchIndex) + rest;
        } else {
            request.setRequestedSessionURL(false);
            request.setRequestSessionId(null);
        }

        // 处理为标准的相对uri
        String normalizedUri = normalize(uri);
        request.setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null) {
            request.setRequestURI(normalizedUri);
        } else {
            request.setRequestURI(uri);
        }
        if (normalizedUri == null) {
            throw new ServletException("Invalid URI: " + uri + "'");
        }
    }

    /**
     * 解析请求信息
     */
    private void parseHeaders(SocketInputStream input, Request request)
            throws IOException, ServletException {
        // 循环获取
        while (true) {
            HttpHeader header = new HttpHeader();

            input.readHeader(header);
            if (header.nameEnd == 0) {
                if (header.valueEnd == 0) {
                    return;
                } else {
                    throw new ServletException();
                }
            }
            String name = header.getName();
            String value = header.getValue();
            request.addHeader(name, value);

            switch (name) {
                case "cookie":
                    // 保存cookie
                    getCookie(value, request);
                    break;
                case "content-length":
                    getContentLength(value, request);
                    break;
                case "content-type":
                    request.setContentType(value);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取并保存cookie.
     */
    private void getCookie(String value, Request request) {
        Cookie[] cookies = RequestUtil.parseCookieHeader(value);
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jsessionid")) {
                if (!request.isRequestedSessionIdFromCookie()) {
                    request.setRequestSessionId(cookie.getValue());
                    request.setRequestedSessionCookie(true);
                    request.setRequestedSessionURL(false);
                }
            }
            request.addCookie(cookie);
        }
    }

    /**
     * 获取content-length.
     */
    private void getContentLength(String value, Request request) throws ServletException {
        int n;
        try {
            n = Integer.parseInt(value);
        } catch (Exception e) {
            throw new ServletException();
        }
        request.setContentLength(n);
    }

    /**
     * 处理标准的相对uri.
     */
    private String normalize(String path) {
        if (path == null)
            return null;
        String normalized = path;

        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);

        // 防止编码'％'，'/'，'。' 和'\'等特殊字符
        if ((normalized.contains("%25"))
                || (normalized.contains("%2F"))
                || (normalized.contains("%2E"))
                || (normalized.contains("%5C"))
                || (normalized.contains("%2f"))
                || (normalized.contains("%2e"))
                || (normalized.contains("%5c"))) {
            return null;
        }

        if (normalized.equals("/."))
            return "/";

        // 将'\\' 转为'/'
        if (normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // 除去 '//'
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // 除去'/./'
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // 除去’/../‘
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // 声明 '/...'无效
        if (normalized.contains("/..."))
            return (null);

        return (normalized);
    }

}
