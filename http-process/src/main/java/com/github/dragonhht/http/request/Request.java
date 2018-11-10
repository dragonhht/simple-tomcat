package com.github.dragonhht.http.request;

import com.github.dragonhht.http.utils.ParameterMap;
import com.github.dragonhht.http.utils.RequestUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.Principal;
import java.util.*;

/**
 * Request类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
final class Request implements HttpServletRequest {

    private InputStream input;
    private String httpContent;

    private HashMap headers = new HashMap();
    private ArrayList<Cookie> cookies = new ArrayList<>();

    private String method;
    private String protocol;
    private String queryString;
    private String requestURI;
    private String requestedSessionId;
    private boolean requestedSessionURL;
    private boolean requestedSessionCookie;
    private String contentType;
    private int contentLength;
    private String characterEncoding;

    /** 控制参数是否写入. */
    private boolean parsed = false;
    private ParameterMap parameters;

    private BufferedReader reader;
    private ServletInputStream stream;

    public Request(InputStream input) {
        this.input = input;
    }

    /**
     * 添加头信息.
     */
    public void addHeader(String key, String value) {
        key = key.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(key);
            if (values == null) {
                values = new ArrayList();
                headers.put(key, values);
            }
            values.add(value);
        }
    }

    public void addCookie(Cookie cookie) {
        synchronized (cookies) {
            cookies.add(cookie);
        }
    }

    /**
     * 解析参数.
     */
    protected void parseParameters() {
        if (parsed) return;

        ParameterMap results = parameters;
        if (results == null)
            results = new ParameterMap();
        results.setLocked(false);
        String encoding = getCharacterEncoding();
        if (encoding == null)
            encoding = "ISO-8859-1";

        // 解析uri中的参数
        String queryString = getQueryString();
        try {
            RequestUtil.parseParameters(results, queryString, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // post请求中的参数解析(表单数据)
        String contentType = getContentType();
        if (contentType == null)
            contentType = "";
        int index = contentType.indexOf(';');
        if (index >= 0) {
            contentType = contentType.substring(0, index).trim();
        } else {
            contentType = contentType.trim();
        }
        if ("POST".equals(getMethod()) && getContentLength() > 0
                && "application/x-www-form-urlencoded".equals(contentType)) {
            try {
                int max = getContentLength();
                int len = 0;
                byte[] buf = new byte[getContentLength()];
                ServletInputStream sis = getInputStream();
                // 将内容放入byte数组内
                while (len < max) {
                    int next = sis.read(buf, len, max - len);
                    if (next < 0)
                        break;
                    len += next;
                }
                sis.close();
                if (len < max)
                    throw new RuntimeException("Content length mismatch");
                RequestUtil.parseParameters(results, buf, encoding);
            } catch (UnsupportedEncodingException ue) {
                ue.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException("Content read fail");
            }
        }

        results.setLocked(true);
        parsed = true;
        parameters = results;
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String name) {
        return 0;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {

    }

    @Override
    public void logout() throws ServletException {

    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        this.characterEncoding = env;
    }

    @Override
    public int getContentLength() {
        return this.contentLength;
    }

    @Override
    public long getContentLengthLong() {
        return this.contentLength;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        if (reader != null)
            throw new IllegalStateException("getInputStream has been called");

        if (stream == null)
            stream = createInputStream();
        return stream;
    }

    public ServletInputStream createInputStream() {
        return new RequestStream(this);
    }

    public InputStream getStream() {
        return this.input;
    }

    @Override
    public String getParameter(String name) {
        parseParameters();
        String[] values = (String[]) parameters.get(name);
        if (values != null) {
            return values[0];
        } else {
            return null;
        }
    }

    @Override
    public Enumeration<String> getParameterNames() {
        parseParameters();
        // TODO 还要处理
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        parseParameters();
        String[] values = (String[]) parameters.get(name);
        if (values != null) {
            return values;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        parseParameters();
        return parameters;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (stream != null)
            throw new IllegalStateException("getInputStream has been called.");
        if (reader == null) {
            String encoding = getCharacterEncoding();
            if (encoding == null)
                encoding = "ISO-8859-1";
            InputStreamReader isr = new InputStreamReader(createInputStream(), encoding);
            reader = new BufferedReader(isr);
        }
        return reader;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    @Override
    public String getRealPath(String path) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setRequestSessionId(String id) {
        this.requestedSessionId = id;
    }

    public void setRequestedSessionURL(boolean flag) {
        requestedSessionURL = flag;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setRequestedSessionCookie(boolean flag) {
        this.requestedSessionCookie = flag;
    }
}
