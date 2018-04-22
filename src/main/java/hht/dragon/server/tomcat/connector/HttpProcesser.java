package hht.dragon.server.tomcat.connector;

import hht.dragon.server.tomcat.Processor.ServletDoProcessor;
import hht.dragon.server.tomcat.Processor.StaticResourceDoProcessor;
import hht.dragon.server.tomcat.request.Request;
import hht.dragon.server.tomcat.response.Response;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 负责创建Request和Response实例.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class HttpProcesser {

    private HttpRequestLine requestLine = new HttpRequestLine();
    private Request request;

    public void process(Socket socket) {
        InputStream input;
        OutputStream output;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
            SocketInputStream socketInputStream = new SocketInputStream(input, 2048);

            request = new Request(socketInputStream);

            // 解析http
            parseRequest(socketInputStream);
            // 解析请求信息
            parseHeaders(socketInputStream);

            Response response = new Response(output);
            response.setRequest(request);

            if (request.getRequestURI().startsWith("/servlet/")) {
                ServletDoProcessor processor = new ServletDoProcessor();
                processor.process(request, response);
            } else {
                StaticResourceDoProcessor processor = new StaticResourceDoProcessor();
                processor.process(request, response);
            }

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析请求行.
     * @param input
     * @throws IOException
     */
    private void parseRequest(SocketInputStream input) throws IOException, ServletException {
        input.readRequestLine(requestLine);
        String method = requestLine.getMethod();
        String uri = null;
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
     * @param input
     * @throws IOException
     * @throws ServletException
     */
    private void parseHeaders(SocketInputStream input)
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

            if (name.equals("cookie")) {

                // TODO 保存cookie

            } else if (name.equals("content-length")) {
                int n = -1;
                try {
                    n = Integer.parseInt(value);
                } catch (Exception e) {
                    throw new ServletException();
                }
                request.setContentLength(n);
            } else if (name.equals("content-type")) {
                request.setContentType(value);
            }
        }
    }

    /**
     * 处理标准的相对uri。
     * @param path
     * @return
     */
    private String normalize(String path) {
        if (path == null)
            return null;
        String normalized = path;

        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);

        // 防止编码'％'，'/'，'。' 和'\'等特殊字符
        if ((normalized.indexOf("%25") >= 0)
                || (normalized.indexOf("%2F") >= 0)
                || (normalized.indexOf("%2E") >= 0)
                || (normalized.indexOf("%5C") >= 0)
                || (normalized.indexOf("%2f") >= 0)
                || (normalized.indexOf("%2e") >= 0)
                || (normalized.indexOf("%5c") >= 0)) {
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
        if (normalized.indexOf("/...") >= 0)
            return (null);

        return (normalized);
    }

}
