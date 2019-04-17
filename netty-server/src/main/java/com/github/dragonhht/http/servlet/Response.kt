package com.github.dragonhht.http.servlet

import java.io.IOException
import java.io.OutputStream
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
class Response constructor(var out: OutputStream): HttpServletResponse {
    private var writer: PrintWriter? = null
    private var servletOutputStream: ServletOutputStream? = null

    override fun getCharacterEncoding(): String? {
        return null
    }

    override fun getContentType(): String? {
        return null
    }

    @Throws(IOException::class)
    override fun getOutputStream(): ServletOutputStream? {
        if (this.servletOutputStream != null) {
            return this.servletOutputStream
        }
        this.servletOutputStream = ResponseOutputStream(this.out)
        return this.servletOutputStream
    }

    @Throws(IOException::class)
    override fun getWriter(): PrintWriter? {
        writer = PrintWriter(out, true)
        return writer
    }

    override fun setCharacterEncoding(charset: String) {

    }

    override fun setContentLength(len: Int) {

    }

    override fun setContentLengthLong(len: Long) {

    }

    override fun setContentType(type: String) {

    }

    override fun setBufferSize(size: Int) {

    }

    override fun getBufferSize(): Int {
        return 0
    }

    @Throws(IOException::class)
    override fun flushBuffer() {

    }

    override fun resetBuffer() {

    }

    override fun isCommitted(): Boolean {
        return false
    }

    override fun reset() {

    }

    override fun setLocale(loc: Locale) {

    }

    override fun getLocale(): Locale? {
        return null
    }

    override fun addCookie(cookie: Cookie) {

    }

    override fun containsHeader(s: String): Boolean {
        return false
    }

    override fun encodeURL(s: String): String? {
        return null
    }

    override fun encodeRedirectURL(s: String): String? {
        return null
    }

    override fun encodeUrl(s: String): String? {
        return null
    }

    override fun encodeRedirectUrl(s: String): String? {
        return null
    }

    @Throws(IOException::class)
    override fun sendError(i: Int, s: String) {

    }

    @Throws(IOException::class)
    override fun sendError(i: Int) {

    }

    @Throws(IOException::class)
    override fun sendRedirect(s: String) {

    }

    override fun setDateHeader(s: String, l: Long) {

    }

    override fun addDateHeader(s: String, l: Long) {

    }

    override fun setHeader(s: String, s1: String) {

    }

    override fun addHeader(s: String, s1: String) {

    }

    override fun setIntHeader(s: String, i: Int) {

    }

    override fun addIntHeader(s: String, i: Int) {

    }

    override fun setStatus(i: Int) {

    }

    override fun setStatus(i: Int, s: String) {

    }

    override fun getStatus(): Int {
        return 0
    }

    override fun getHeader(s: String): String? {
        return null
    }

    override fun getHeaders(s: String): Collection<String>? {
        return null
    }

    override fun getHeaderNames(): Collection<String>? {
        return null
    }
}