package hht.dragon.server.tomcat.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * 使用外观类，屏蔽掉Response中特有的方法，防止servlet类中的service方法调用.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class ResponseFacade implements ServletResponse {

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
}
