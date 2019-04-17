package com.github.dragonhht.http.servlet

import java.io.*
import java.security.Principal
import java.util.*
import javax.servlet.*
import javax.servlet.http.*

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class Request: HttpServletRequest {

    private lateinit var input: InputStream
    private val httpContent: String? = null

    private val headers = HashMap<String, String>()
    private val cookies = ArrayList<Cookie>()

    private lateinit var method: String
    private lateinit var protocol: String
    private var queryString: String? = null
    private lateinit var requestURI: String
    private var requestedSessionId: String? = null
    private var requestedSessionURL: Boolean = false
    private var requestedSessionCookie: Boolean = false
    private lateinit var contentType: String
    private var contentLength: Int = 0
    private var characterEncoding: String? = null

    /** 控制参数是否写入.  */
    private var parsed = false
    private var parameters: ParameterMap<String, Array<String>?> = ParameterMap()

    private var reader: BufferedReader? = null
    private var stream: ServletInputStream? = null

    /**
     * 添加头信息.
     */
    fun addHeader(key: String, value: String) {
        var key = key
        key = key.toLowerCase()
        synchronized(headers) {
            headers[key] = value
        }
    }

    fun addCookie(cookie: Cookie) {
        synchronized(cookies) {
            cookies.add(cookie)
        }
    }

    override fun getAuthType(): String? {
        return null
    }

    override fun getCookies(): Array<Cookie> {
        return cookies.toTypedArray()
    }

    override fun getDateHeader(name: String): Long {
        return 0
    }

    override fun getHeader(name: String): String? {
        return headers[name]
    }

    override fun getHeaders(name: String): Enumeration<String>? {
        return null
    }

    override fun getHeaderNames(): Enumeration<String>? {
        return null
    }

    override fun getIntHeader(name: String): Int {
        return 0
    }

    override fun getMethod(): String {
        return this.method
    }

    override fun getPathInfo(): String? {
        return null
    }

    override fun getPathTranslated(): String? {
        return null
    }

    override fun getContextPath(): String? {
        return null
    }

    override fun getQueryString(): String? {
        return this.queryString
    }

    override fun getRemoteUser(): String? {
        return null
    }

    override fun isUserInRole(role: String): Boolean {
        return false
    }

    override fun getUserPrincipal(): Principal? {
        return null
    }

    override fun getRequestedSessionId(): String? {
        return this.requestedSessionId
    }

    override fun getRequestURI(): String {
        return this.requestURI
    }

    override fun getRequestURL(): StringBuffer? {
        return null
    }

    override fun getServletPath(): String? {
        return null
    }

    override fun getSession(create: Boolean): HttpSession? {
        return null
    }

    override fun getSession(): HttpSession? {
        return null
    }

    override fun changeSessionId(): String? {
        return null
    }

    override fun isRequestedSessionIdValid(): Boolean {
        return false
    }

    override fun isRequestedSessionIdFromCookie(): Boolean {
        return false
    }

    override fun isRequestedSessionIdFromURL(): Boolean {
        return false
    }

    override fun isRequestedSessionIdFromUrl(): Boolean {
        return false
    }

    @Throws(IOException::class, ServletException::class)
    override fun authenticate(response: HttpServletResponse): Boolean {
        return false
    }

    @Throws(ServletException::class)
    override fun login(username: String, password: String) {

    }

    @Throws(ServletException::class)
    override fun logout() {

    }

    @Throws(IOException::class, ServletException::class)
    override fun getParts(): Collection<Part>? {
        return null
    }

    @Throws(IOException::class, ServletException::class)
    override fun getPart(name: String): Part? {
        return null
    }

    @Throws(IOException::class, ServletException::class)
    override fun <T : HttpUpgradeHandler> upgrade(handlerClass: Class<T>): T? {
        return null
    }

    override fun getAttribute(name: String): Any? {
        return null
    }

    override fun getAttributeNames(): Enumeration<String>? {
        return null
    }

    override fun getCharacterEncoding(): String? {
        return this.characterEncoding
    }

    @Throws(UnsupportedEncodingException::class)
    override fun setCharacterEncoding(env: String) {
        this.characterEncoding = env
    }

    override fun getContentLength(): Int {
        return this.contentLength
    }

    override fun getContentLengthLong(): Long {
        return this.contentLength.toLong()
    }

    override fun getContentType(): String {
        return this.contentType
    }

    fun setInputStream(input: InputStream) {
        this.input = input
    }

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream? {
        if (reader != null)
            throw IllegalStateException("getInputStream has been called")
        if (stream == null)
            stream = createInputStream()
        return stream
    }

    fun createInputStream(): ServletInputStream {
        return RequestStream(this)
    }

    fun getStream(): InputStream {
        return this.input
    }

    override fun getParameter(name: String): String? {
        val value = parameters[name]
        return if (value != null) {
            value[0]
        } else {
            null
        }
    }

    fun  setParameter(key: String, value: Array<String>) {
        this.parameters[key] = value
    }

    fun setParameter(key: String, value: String?) {
        if (value != null) {
            this.parameters[key] = arrayOf(value)
        }
    }

    override fun getParameterNames(): Enumeration<String>? {
        // TODO 还要处理
        return null
    }

    override fun getParameterValues(name: String): Array<String>? {
        return parameters[name]
    }

    override fun getParameterMap(): Map<String, Array<String>?> {
        return parameters
    }

    override fun getProtocol(): String {
        return this.protocol
    }

    override fun getScheme(): String? {
        return null
    }

    override fun getServerName(): String? {
        return null
    }

    override fun getServerPort(): Int {
        return 0
    }

    @Throws(IOException::class)
    override fun getReader(): BufferedReader? {
        if (stream != null)
            throw IllegalStateException("getInputStream has been called.")
        if (reader == null) {
            var encoding: String? = getCharacterEncoding()
            if (encoding == null)
                encoding = "ISO-8859-1"
            val isr = InputStreamReader(createInputStream(), encoding)
            reader = BufferedReader(isr)
        }
        return reader
    }

    override fun getRemoteAddr(): String? {
        return null
    }

    override fun getRemoteHost(): String? {
        return null
    }

    override fun setAttribute(name: String, o: Any) {

    }

    override fun removeAttribute(name: String) {

    }

    override fun getLocale(): Locale? {
        return null
    }

    override fun getLocales(): Enumeration<Locale>? {
        return null
    }

    override fun isSecure(): Boolean {
        return false
    }

    override fun getRequestDispatcher(path: String): RequestDispatcher? {
        return null
    }

    override fun getRealPath(path: String): String? {
        return null
    }

    override fun getRemotePort(): Int {
        return 0
    }

    override fun getLocalName(): String? {
        return null
    }

    override fun getLocalAddr(): String? {
        return null
    }

    override fun getLocalPort(): Int {
        return 0
    }

    override fun getServletContext(): ServletContext? {
        return null
    }

    @Throws(IllegalStateException::class)
    override fun startAsync(): AsyncContext? {
        return null
    }

    @Throws(IllegalStateException::class)
    override fun startAsync(servletRequest: ServletRequest, servletResponse: ServletResponse): AsyncContext? {
        return null
    }

    override fun isAsyncStarted(): Boolean {
        return false
    }

    override fun isAsyncSupported(): Boolean {
        return false
    }

    override fun getAsyncContext(): AsyncContext? {
        return null
    }

    override fun getDispatcherType(): DispatcherType? {
        return null
    }

    fun setQueryString(queryString: String) {
        this.queryString = queryString
    }

    fun setRequestSessionId(id: String) {
        this.requestedSessionId = id
    }

    fun setRequestedSessionURL(flag: Boolean) {
        requestedSessionURL = flag
    }

    fun setMethod(method: String) {
        this.method = method
    }

    fun setProtocol(protocol: String) {
        this.protocol = protocol
    }

    fun setRequestURI(requestURI: String) {
        this.requestURI = requestURI
    }

    fun setContentType(contentType: String) {
        this.contentType = contentType
    }

    fun setContentLength(contentLength: Int) {
        this.contentLength = contentLength
    }

    fun setRequestedSessionCookie(flag: Boolean) {
        this.requestedSessionCookie = flag
    }
}