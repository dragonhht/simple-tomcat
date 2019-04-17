package com.github.dragonhht.http.servlet

import java.io.IOException
import java.io.PrintWriter
import java.util.*
import javax.servlet.ServletOutputStream
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class ResponseFacade(private val response: Response) : HttpServletResponse {

    override fun getCharacterEncoding(): String? {
        return this.response.characterEncoding
    }

    override fun getContentType(): String? {
        return this.response.contentType
    }

    @Throws(IOException::class)
    override fun getOutputStream(): ServletOutputStream? {
        return this.response.outputStream
    }

    @Throws(IOException::class)
    override fun getWriter(): PrintWriter? {
        return this.response.writer
    }

    override fun setCharacterEncoding(charset: String) {
        this.response.setCharacterEncoding(charset)
    }

    override fun setContentLength(len: Int) {
        this.response.setContentLength(len)
    }

    override fun setContentLengthLong(len: Long) {
        this.response.setContentLengthLong(len)
    }

    override fun setContentType(type: String) {
        this.response.setContentType(type)
    }

    override fun setBufferSize(size: Int) {
        this.response.bufferSize = size
    }

    override fun getBufferSize(): Int {
        return this.response.bufferSize
    }

    @Throws(IOException::class)
    override fun flushBuffer() {
        this.response.flushBuffer()
    }

    override fun resetBuffer() {
        this.response.resetBuffer()
    }

    override fun isCommitted(): Boolean {
        return this.response.isCommitted
    }

    override fun reset() {
        this.response.reset()
    }

    override fun setLocale(loc: Locale) {
        this.response.setLocale(loc)
    }

    override fun getLocale(): Locale? {
        return this.response.locale
    }

    override fun addCookie(cookie: Cookie) {
        this.response.addCookie(cookie)
    }

    override fun containsHeader(s: String): Boolean {
        return this.response.containsHeader(s)
    }

    override fun encodeURL(s: String): String? {
        return this.response.encodeURL(s)
    }

    override fun encodeRedirectURL(s: String): String? {
        return this.response.encodeRedirectURL(s)
    }

    override fun encodeUrl(s: String): String? {
        return this.response.encodeUrl(s)
    }

    override fun encodeRedirectUrl(s: String): String? {
        return this.response.encodeRedirectUrl(s)
    }

    @Throws(IOException::class)
    override fun sendError(i: Int, s: String) {
        this.response.sendError(i, s)
    }

    @Throws(IOException::class)
    override fun sendError(i: Int) {
        this.response.sendError(i)
    }

    @Throws(IOException::class)
    override fun sendRedirect(s: String) {
        this.response.sendRedirect(s)
    }

    override fun setDateHeader(s: String, l: Long) {
        this.response.setDateHeader(s, l)
    }

    override fun addDateHeader(s: String, l: Long) {
        this.response.addDateHeader(s, l)
    }

    override fun setHeader(s: String, s1: String) {
        this.response.setHeader(s, s1)
    }

    override fun addHeader(s: String, s1: String) {
        this.response.addHeader(s, s1)
    }

    override fun setIntHeader(s: String, i: Int) {
        this.response.setIntHeader(s, i)
    }

    override fun addIntHeader(s: String, i: Int) {
        this.response.addIntHeader(s, i)
    }

    override fun setStatus(i: Int) {
        this.response.status = i
    }

    override fun setStatus(i: Int, s: String) {
        this.response.setStatus(i, s)
    }

    override fun getStatus(): Int {
        return this.response.status
    }

    override fun getHeader(s: String): String? {
        return this.response.getHeader(s)
    }

    override fun getHeaders(s: String): Collection<String>? {
        return this.response.getHeaders(s)
    }

    override fun getHeaderNames(): Collection<String>? {
        return this.response.headerNames
    }
}