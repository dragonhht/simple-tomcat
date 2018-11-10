package com.github.dragonhht.http.request;

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
                }else {
                    throw new IOException();
                }
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
                } else {
                    throw new IOException();
                }
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
        maxRead = requestLine.protocol.length;
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
                } else {
                    throw new IOException();
                }
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

    /**
     * 读取Http的请求头信息.
     * @param header
     * @throws IOException
     */
    public void readHeader(HttpHeader header) throws IOException {
        if (header.nameEnd != 0)
            header.recycle();

        int chr = read();
        if (chr == CR || chr == LF) {
            // 遇到 CR 则表示一行已结束(跳过 LF)
            if (chr == CR)
                read();
            header.nameEnd = 0;
            header.valueEnd = 0;
            return;
        } else {
            pos--;
        }

        // 读取请求头信息的name
        int maxRead = header.name.length;
        int readStart = pos;
        int readCount = 0;

        boolean colon = false;

        while (!colon) {
            if (readCount >= maxRead) {
                if (2 * maxRead <= HttpHeader.MAX_NAME_SIZE) {
                    char[] newBuf = new char[2 * maxRead];
                    System.arraycopy(header, 0, newBuf, 0, maxRead);
                    header.name = newBuf;
                    maxRead = header.name.length;
                } else {
                    throw new EOFException();
                }
            }

            if (pos >= count) {
                int val = read();
                if (val == -1)
                    throw new IOException();
                pos = 0;
                readStart = 0;
            }
            if (buf[pos] == COLON)
                colon = true;

            char val = (char) buf[pos];
            if ((val >= 'A') && (val <= 'Z') ) {
                // 转为小写
                val = (char) (val - LC_OFFSET);
            }
            header.name[readCount] = val;
            pos++;
            readCount++;
        }
        header.nameEnd = readCount - 1;

        // 读取请求头信息的value

        maxRead = header.value.length;
        readStart = pos;
        readCount = 0;

        int crPos = -2;

        boolean eol = false;
        boolean validLine = true;

        while (validLine) {

            boolean space = false;

            // 跳过空格
            while (!space) {
                if (pos >= count) {
                    int val = read();
                    if (val == -1)
                        throw new IOException();
                    pos = 0;
                    readStart = 0;
                }
                if ((buf[pos] == SP) || (buf[pos] == HT)) {
                    pos++;
                } else {
                    space = true;
                }
            }

            // 读取值
            while (!eol) {
                if (readCount >= maxRead) {
                    if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
                        char[] newBuf = new char[2 * maxRead];
                        System.arraycopy(header.value, 0, newBuf, 0, maxRead);
                        header.value = newBuf;
                        maxRead = header.value.length;
                    } else {
                        throw new IOException();
                    }
                }

                if (pos >= count) {
                    int val = read();
                    if (val == -1)
                        throw new IOException();
                    pos = 0;
                    readStart = 0;
                }

                if (buf[pos] == CR) {

                } else if (buf[pos] ==LF) {
                    eol = true;
                } else {
                    int ch = buf[pos] & 0xff;
                    header.value[readCount] = (char) ch;
                    readCount++;
                }
                pos++;
            }

            int nextChr = read();

            if ((nextChr != SP) && (nextChr != HT)) {
                pos--;
                validLine = false;
            } else {
                eol = false;
                if (readCount >= maxRead) {
                    if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
                        char[] newBuffer = new char[2 * maxRead];
                        System.arraycopy(header.value, 0, newBuffer, 0,
                                maxRead);
                        header.value = newBuffer;
                        maxRead = header.value.length;
                    } else {
                        throw new IOException();
                    }
                }
                header.value[readCount] = ' ';
                readCount++;
            }
        }

        header.valueEnd = readCount;

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
        int len = is.read(buf);
        if (len > 0) {
            count = len;
        }
    }

    public void close() throws IOException {
        if (is == null)
            return;
        is.close();
        is = null;
        buf = null;
    }
}
