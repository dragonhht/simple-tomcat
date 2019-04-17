package com.github.dragonhht.http.servlet

import java.io.BufferedReader
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.Principal
import java.util.*
import javax.servlet.*
import javax.servlet.http.*

/**
 * 使用外观类，屏蔽掉Request中特有的方法，防止servlet类中的service方法调用.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class RequestFacade(private val request: Request) : HttpServletRequest {


    override fun getAuthType(): String? {
        return this.request.authType
    }

    override fun getCookies(): Array<Cookie> {
        return this.request.cookies
    }

    override fun getDateHeader(name: String): Long {
        return this.request.getDateHeader(name)
    }

    override fun getHeader(name: String): String? {
        return this.request.getHeader(name)
    }

    override fun getHeaders(name: String): Enumeration<String>? {
        return this.request.getHeaders(name)
    }

    override fun getHeaderNames(): Enumeration<String>? {
        return this.request.headerNames
    }

    override fun getIntHeader(name: String): Int {
        return this.request.getIntHeader(name)
    }

    override fun getMethod(): String {
        return this.request.method
    }

    override fun getPathInfo(): String? {
        return this.request.pathInfo
    }

    override fun getPathTranslated(): String? {
        return this.request.pathTranslated
    }

    override fun getContextPath(): String? {
        return this.request.contextPath
    }

    override fun getQueryString(): String? {
        return this.request.queryString
    }

    override fun getRemoteUser(): String? {
        return this.request.remoteUser
    }

    override fun isUserInRole(role: String): Boolean {
        return this.request.isUserInRole(role)
    }

    override fun getUserPrincipal(): Principal? {
        return this.request.userPrincipal
    }

    override fun getRequestedSessionId(): String? {
        return this.request.requestedSessionId
    }

    override fun getRequestURI(): String {
        return this.request.requestURI
    }

    override fun getRequestURL(): StringBuffer? {
        return this.request.requestURL
    }

    override fun getServletPath(): String? {
        return this.request.servletPath
    }

    override fun getSession(create: Boolean): HttpSession? {
        return this.request.getSession(create)
    }

    override fun getSession(): HttpSession? {
        return this.request.session
    }

    override fun changeSessionId(): String? {
        return this.request.changeSessionId()
    }

    override fun isRequestedSessionIdValid(): Boolean {
        return this.request.isRequestedSessionIdValid
    }

    override fun isRequestedSessionIdFromCookie(): Boolean {
        return this.request.isRequestedSessionIdFromCookie
    }

    override fun isRequestedSessionIdFromURL(): Boolean {
        return this.request.isRequestedSessionIdFromURL
    }

    override fun isRequestedSessionIdFromUrl(): Boolean {
        return this.request.isRequestedSessionIdFromUrl
    }

    @Throws(IOException::class, ServletException::class)
    override fun authenticate(response: HttpServletResponse): Boolean {
        return this.request.authenticate(response)
    }

    @Throws(ServletException::class)
    override fun login(username: String, password: String) {
        this.request.login(username, password)
    }

    @Throws(ServletException::class)
    override fun logout() {
        this.request.logout()
    }

    @Throws(IOException::class, ServletException::class)
    override fun getParts(): Collection<Part>? {
        return this.request.parts
    }

    @Throws(IOException::class, ServletException::class)
    override fun getPart(name: String): Part? {
        return this.request.getPart(name)
    }

    @Throws(IOException::class, ServletException::class)
    override fun <T : HttpUpgradeHandler> upgrade(handlerClass: Class<T>): T? {
        return this.request.upgrade(handlerClass)
    }

    override fun getAttribute(name: String): Any? {
        return this.request.getAttribute(name)
    }

    override fun getAttributeNames(): Enumeration<String>? {
        return this.request.attributeNames
    }

    override fun getCharacterEncoding(): String? {
        return this.request.characterEncoding
    }

    @Throws(UnsupportedEncodingException::class)
    override fun setCharacterEncoding(env: String) {
        this.request.setCharacterEncoding(env)
    }

    override fun getContentLength(): Int {
        return this.request.contentLength
    }

    override fun getContentLengthLong(): Long {
        return this.request.contentLengthLong
    }

    override fun getContentType(): String {
        return this.request.contentType
    }

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream? {
        return this.request.inputStream
    }

    override fun getParameter(name: String): String? {
        return this.request.getParameter(name)
    }

    override fun getParameterNames(): Enumeration<String>? {
        return this.request.parameterNames
    }

    override fun getParameterValues(name: String): Array<String>? {
        return this.request.getParameterValues(name)
    }

    override fun getParameterMap(): Map<String, Array<String>?> {
        return this.request.parameterMap
    }

    override fun getProtocol(): String {
        return this.request.protocol
    }

    override fun getScheme(): String? {
        return this.request.scheme
    }

    override fun getServerName(): String? {
        return this.request.serverName
    }

    override fun getServerPort(): Int {
        return this.request.serverPort
    }

    @Throws(IOException::class)
    override fun getReader(): BufferedReader? {
        return this.request.reader
    }

    override fun getRemoteAddr(): String? {
        return this.request.remoteAddr
    }

    override fun getRemoteHost(): String? {
        return this.request.remoteHost
    }

    override fun setAttribute(name: String, o: Any) {
        this.request.setAttribute(name, o)
    }

    override fun removeAttribute(name: String) {
        this.request.removeAttribute(name)
    }

    override fun getLocale(): Locale? {
        return this.request.locale
    }

    override fun getLocales(): Enumeration<Locale>? {
        return this.request.locales
    }

    override fun isSecure(): Boolean {
        return this.request.isSecure
    }

    override fun getRequestDispatcher(path: String): RequestDispatcher? {
        return this.request.getRequestDispatcher(path)
    }

    override fun getRealPath(path: String): String? {
        return this.request.getRealPath(path)
    }

    override fun getRemotePort(): Int {
        return this.request.remotePort
    }

    override fun getLocalName(): String? {
        return this.request.localName
    }

    override fun getLocalAddr(): String? {
        return this.request.localAddr
    }

    override fun getLocalPort(): Int {
        return this.request.localPort
    }

    override fun getServletContext(): ServletContext? {
        return this.request.servletContext
    }

    @Throws(IllegalStateException::class)
    override fun startAsync(): AsyncContext? {
        return this.request.startAsync()
    }

    @Throws(IllegalStateException::class)
    override fun startAsync(servletRequest: ServletRequest, servletResponse: ServletResponse): AsyncContext? {
        return this.request.startAsync(servletRequest, servletResponse)
    }

    override fun isAsyncStarted(): Boolean {
        return this.request.isAsyncStarted
    }

    override fun isAsyncSupported(): Boolean {
        return this.request.isAsyncSupported
    }

    override fun getAsyncContext(): AsyncContext? {
        return this.request.asyncContext
    }

    override fun getDispatcherType(): DispatcherType? {
        return this.request.dispatcherType
    }
}