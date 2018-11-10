package com.github.dragonhht.http.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

/**
 * 使用外观类，屏蔽掉Response中特有的方法，防止servlet类中的service方法调用.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class ResponseFacade implements HttpServletResponse {

    private Response response;

    public ResponseFacade(Response response) {
        this.response = response;
    }

    @Override
    public String getCharacterEncoding() {
        return this.response.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return this.response.getContentType();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return this.response.getWriter();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        this.response.setCharacterEncoding(charset);
    }

    @Override
    public void setContentLength(int len) {
        this.response.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long len) {
        this.response.setContentLengthLong(len);
    }

    @Override
    public void setContentType(String type) {
        this.response.setContentType(type);
    }

    @Override
    public void setBufferSize(int size) {
        this.response.setBufferSize(size);
    }

    @Override
    public int getBufferSize() {
        return this.response.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        this.response.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        this.response.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return this.response.isCommitted();
    }

    @Override
    public void reset() {
        this.response.reset();
    }

    @Override
    public void setLocale(Locale loc) {
        this.response.setLocale(loc);
    }

    @Override
    public Locale getLocale() {
        return this.response.getLocale();
    }

    @Override
    public void addCookie(Cookie cookie) {
        this.response.addCookie(cookie);
    }

    @Override
    public boolean containsHeader(String s) {
        return this.response.containsHeader(s);
    }

    @Override
    public String encodeURL(String s) {
        return this.response.encodeURL(s);
    }

    @Override
    public String encodeRedirectURL(String s) {
        return this.response.encodeRedirectURL(s);
    }

    @Override
    public String encodeUrl(String s) {
        return this.response.encodeUrl(s);
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return this.response.encodeRedirectUrl(s);
    }

    @Override
    public void sendError(int i, String s) throws IOException {
        this.response.sendError(i, s);
    }

    @Override
    public void sendError(int i) throws IOException {
        this.response.sendError(i);
    }

    @Override
    public void sendRedirect(String s) throws IOException {
        this.response.sendRedirect(s);
    }

    @Override
    public void setDateHeader(String s, long l) {
        this.response.setDateHeader(s, l);
    }

    @Override
    public void addDateHeader(String s, long l) {
        this.response.addDateHeader(s, l);
    }

    @Override
    public void setHeader(String s, String s1) {
        this.response.setHeader(s, s1);
    }

    @Override
    public void addHeader(String s, String s1) {
        this.response.addHeader(s, s1);
    }

    @Override
    public void setIntHeader(String s, int i) {
        this.response.setIntHeader(s, i);
    }

    @Override
    public void addIntHeader(String s, int i) {
        this.response.addIntHeader(s, i);
    }

    @Override
    public void setStatus(int i) {
        this.response.setStatus(i);
    }

    @Override
    public void setStatus(int i, String s) {
        this.response.setStatus(i, s);
    }

    @Override
    public int getStatus() {
        return this.response.getStatus();
    }

    @Override
    public String getHeader(String s) {
        return this.response.getHeader(s);
    }

    @Override
    public Collection<String> getHeaders(String s) {
        return this.response.getHeaders(s);
    }

    @Override
    public Collection<String> getHeaderNames() {
        return this.response.getHeaderNames();
    }
}
