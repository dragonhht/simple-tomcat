package com.github.dragonhht.http.servlet

import java.io.IOException
import java.io.InputStream
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class RequestStream(request: Request) : ServletInputStream() {

    private var closed = false
    /** 已返回的长度.  */
    private var count = 0
    private var length = -1
    private var stream: InputStream

    init {
        this.closed = false
        this.count = 0
        this.length = request.contentLength
        this.stream = request.getStream()
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun isReady(): Boolean {
        return false
    }

    override fun setReadListener(readListener: ReadListener) {

    }

    @Throws(IOException::class)
    override fun read(): Int {
        if (closed)
            throw IOException("requestStream.read.closed")
        if (length in 1..count)
            return -1

        val n = stream.read()
        if (n >= 0)
            count++
        return n
    }

    @Throws(IOException::class)
    override fun read(buf: ByteArray): Int {
        return read(buf, 0, buf.size)
    }

    @Throws(IOException::class)
    override fun read(buf: ByteArray, off: Int, len: Int): Int {
        // 读取的长度
        var toRead = len
        if (length > 0) {
            // 已读取的大于总长度
            if (count >= length)
                return -1
            if (count + len > length)
                toRead = length - count
        }
        return super.read(buf, 0, toRead)
    }

    @Throws(IOException::class)
    override fun close() {
        if (closed)
            throw IOException("requestStream.close.closed")
        if (length > 0) {
            while (count > length) {
                val n = read()
                if (n < 0)
                    break
            }
        }
        closed = true
    }
}