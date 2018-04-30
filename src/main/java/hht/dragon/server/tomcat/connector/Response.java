package hht.dragon.server.tomcat.connector;

import hht.dragon.server.tomcat.connector.Request;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * Response类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class Response implements ServletResponse {

    /** 静态资源文件存放路径. */
    private static final String WEB_ROOT = "WebRoot";

    private OutputStream out;
    private Request request;
    private PrintWriter writer;

    public Response(OutputStream out) {
        this.out = out;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void senStaticResource(String path) {
        StringBuffer outStr = new StringBuffer();
        outStr.append("HTTP/1.1 200 OK\n");
        outStr.append("Content-Type: text/html;charset=UTF-8\n");
        outStr.append("\r\n");
        try {
            out.write(outStr.toString().getBytes());
            getFile(path);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取静态文件.
     * @param path 静态文件路径
     * @throws IOException
     */
    private void getFile(String path) {
        if (path == null) return;
        path = WEB_ROOT + path;
        FileInputStream fis = null;
        try {
            try {
                fis = new FileInputStream(path);

            } catch (FileNotFoundException e) {
                path = WEB_ROOT + "/html/error/404.html";
                fis = new FileInputStream(path);
            }

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writer = new PrintWriter(out, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
