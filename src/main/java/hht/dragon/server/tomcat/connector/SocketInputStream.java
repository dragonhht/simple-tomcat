package hht.dragon.server.tomcat.connector;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 帮助解析Http请求.
 *
 * @author: huang
 * Date: 2018/4/21
 */
public class SocketInputStream extends InputStream {

    /** CR. */
    private static final byte CR = (byte) '\r';


    /** LF. */
    private static final byte LF = (byte) '\n';


    /** SP. */
    private static final byte SP = (byte) ' ';


    /** HT. */
    private static final byte HT = (byte) '\t';


    /** COLON. */
    private static final byte COLON = (byte) ':';


    /** Lower case offset. */
    private static final int LC_OFFSET = 'A' - 'a';


    /** Internal buffer. */
    protected byte buf[];


    /** 可读取的长度. */
    protected int count;


    /** 当前的读取位置. */
    protected int pos;


    /** Underlying input stream. */
    protected InputStream is;

    public SocketInputStream(InputStream is, int bufferSize) {
        this.is = is;
        this.buf =  new byte[bufferSize];
    }

    /**
     * 读取Http请求行信息.
     * @param requestLine
     */
    public void readRequestLine(HttpRequestLine requestLine) throws IOException {
        // 重置requestLine
        if (requestLine.methodEnd != 0) {
            requestLine.recycle();
        }

        int chr = 0;

        // 跳过 CR 及 LF 符
        do {
            try {
                chr = read();
            } catch (IOException e) {
                chr = -1;
            }
        } while (chr == CR || chr == LF);
        if (chr == -1) {
            // TODO 出现错误
            throw new EOFException();
        }
        pos--;

        // 获取请求方法
        int maxRead = requestLine.method.length;
        int readStart = pos;
        int readCount = 0;

        boolean spance = false;

        while (!spance) {
            // 判断存放请求方法的数组大小是否大于现在读取的大小，若小于则扩大数组大小
            if (readCount >= maxRead) {
                if ((2 * maxRead) <= HttpRequestLine.MAX_METHOD_SIZE) {
                    char[] newBuffer = new char[2 * maxRead];
                    System.arraycopy(requestLine.method, 0,
                            newBuffer, 0, maxRead);
                    requestLine.method = newBuffer;
                    maxRead = requestLine.method.length;
                }
            } else {
                throw new IOException();
            }

            // 判断读取的位置是否在读取范围内
            if (pos >= count) {
                int val = read();
                if (val == -1) {
                    throw new IOException();
                }
                pos = 0;
                readStart = 0;
            }

            // 判断是否为空格，若为空格则已获取到请求方法.
            if (buf[pos] == SP) {
                spance = true;
            }
            requestLine.method[readCount] = (char) buf[pos];
            readCount++;
            pos++;
        }
        requestLine.methodEnd = readCount - 1;

        // 读取URI

        maxRead = requestLine.uri.length;
        readStart = pos;
        readCount = 0;
        spance = false;

        while (!spance) {
            // 判断存放URI的数组大小是否大于现在读取的大小，若小于则扩大数组大小
            if (readCount >= maxRead) {
                if ((2 * maxRead) <= HttpRequestLine.MAX_URI_SIZE) {
                    char[] newBuffer = new char[2 * maxRead];
                    System.arraycopy(requestLine.uri, 0,
                            newBuffer, 0, maxRead);
                    requestLine.uri = newBuffer;
                    maxRead = requestLine.uri.length;
                }
            } else {
                throw new IOException();
            }

            // 判断读取的位置是否在读取范围内
            if (pos >= count) {
                int val = read();
                if (val == -1) {
                    throw new IOException();
                }
                pos = 0;
                readStart = 0;
            }

            if (buf[pos] == SP) {
                spance = true;
            }
            requestLine.uri[readCount] = (char) buf[pos];
            readCount++;
            pos++;
        }
        requestLine.uriEnd = readCount - 1;

        // 读取协议信息
        maxRead = requestLine.uri.length;
        readStart = pos;
        readCount = 0;
        spance = false;

        while (!spance) {
            if (readCount >= maxRead) {
                if ((2 * maxRead) <= HttpRequestLine.MAX_PROTOCOL_SIZE) {
                    char[] newBuffer = new char[2 * maxRead];
                    System.arraycopy(requestLine.protocol, 0,
                            newBuffer, 0, maxRead);
                    requestLine.protocol = newBuffer;
                    maxRead = requestLine.protocol.length;
                }
            } else {
                throw new IOException();
            }
            // 判断读取的位置是否在读取范围内
            if (pos >= count) {
                int val = read();
                if (val == -1) {
                    throw new IOException();
                }
                pos = 0;
                readStart = 0;
            }

            if (buf[pos] == SP) {
                // 跳过
            } else if (buf[pos] == LF) {
                spance = true;
            } else {
                requestLine.protocol[readCount] = (char) buf[pos];
                readCount++;
            }
            pos++;
        }
        requestLine.protocolEnd = readCount;
    }

    @Override
    public int read() throws IOException {
        if (pos >= count) {
            // 如果当前的读取位置大于可读的长度，则重置
            fill();
            if (pos >= count) {
                return -1;
            }
        }
        // 将byte转成int
        return buf[pos++] & 0xff;
    }

    /**
     * 重置byte数组的可读取位置及可读取范围.
     * @throws IOException
     */
    private void fill() throws IOException {
        pos = 0;
        count = 0;
        int len = is.read(buf, 0, buf.length);
        if (len > 0) {
            count = len;
        }
    }
}
