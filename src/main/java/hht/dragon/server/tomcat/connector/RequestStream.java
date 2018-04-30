package hht.dragon.server.tomcat.connector;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述.
 *
 * @author: huang
 * Date: 2018/4/30
 */
public class RequestStream extends ServletInputStream {

    private boolean closed = false;
    /** 已返回的长度. */
    private int count = 0;
    private int length = -1;
    private InputStream stream;

    public RequestStream(Request request) {
        super();
        this.closed = false;
        this.count = 0;
        this.length = request.getContentLength();
        this.stream = request.getStream();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        if (closed)
            throw new IOException("requestStream.read.closed");
        if ((length > 0) && (count >= length))
            return -1;

        int n = stream.read();
        if (n >= 0)
            count++;
        return n;
    }

    public int read(byte[] buf) throws IOException {
        return read(buf, 0, buf.length);
    }

    public int read(byte[] buf, int off, int len) throws IOException {
        // 读取的长度
        int toRead = len;
        if (length > 0) {
            // 已读取的大于总长度
            if (count >= length)
                return -1;
            if ((count + len) > length)
                toRead = length - count;
        }
        int actuallyRead = super.read(buf, 0, toRead);
        return actuallyRead;
    }

    public void close() throws IOException {
        if (closed)
            throw new IOException("requestStream.close.closed");
        if (length > 0) {
            while (count > length) {
                int n = read();
                if (n < 0)
                    break;
            }
        }
        closed = true;
    }
}
