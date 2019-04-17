package com.github.dragonhht.http.servlet

import java.io.IOException
import java.io.OutputStream
import javax.servlet.ServletOutputStream
import javax.servlet.WriteListener

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class ResponseOutputStream(private val out: OutputStream) : ServletOutputStream() {

    override fun isReady(): Boolean {
        // TODO 待实现
        return false
    }

    override fun setWriteListener(writeListener: WriteListener) {
        // TODO 待实现
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        // TODO 待实现
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray) {
        this.out.write(b)
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        this.out.write(b, off, len)
    }

    @Throws(IOException::class)
    override fun flush() {
        this.out.flush()
    }

    @Throws(IOException::class)
    override fun close() {
        this.out.close()
    }
}