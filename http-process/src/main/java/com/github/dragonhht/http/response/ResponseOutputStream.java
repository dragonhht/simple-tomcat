package com.github.dragonhht.http.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 描述.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public class ResponseOutputStream extends ServletOutputStream {

    private OutputStream out;

    public ResponseOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public boolean isReady() {
        // TODO 待实现
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        // TODO 待实现
    }

    @Override
    public void write(int b) throws IOException {
        // TODO 待实现
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }
}
