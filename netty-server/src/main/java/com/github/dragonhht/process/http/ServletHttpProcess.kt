package com.github.dragonhht.process.http

import com.github.dragonhht.Bootstrap
import org.slf4j.LoggerFactory
import java.util.regex.Pattern
import javax.servlet.Servlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Servlet请求处理.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class ServletHttpProcess: HttpProcess {

    private val log = LoggerFactory.getLogger(this::class.java)

    private val WEB_ROOT = "D:/my_work_spance/idea_workspance/simple-tomcat/WebRoot/servlet"

    override fun process(request: HttpServletRequest, response: HttpServletResponse) {
        val uri = request.requestURI

        for (entry in Bootstrap.webApp.servlets) {
            val urlPattern = entry.key.urlPattern
            if (Pattern.compile(urlPattern).matcher(uri).matches()) {
                runService(request, response, entry.value.servletClass!!)
                break
            }
        }
    }

    private fun runService(request: HttpServletRequest, response: HttpServletResponse, servletName: String) {
        try {
            val clazz = Bootstrap.classLoader.loadClass(servletName)
            val servlet = clazz.newInstance() as Servlet
            servlet.service(request, response)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}