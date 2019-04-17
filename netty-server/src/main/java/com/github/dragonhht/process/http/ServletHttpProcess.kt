package com.github.dragonhht.process.http

import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLClassLoader
import java.net.URLStreamHandler
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

    private val WEB_ROOT = "D:/my_work_spance/idea_workspance/simple-tomcat/WebRoot/servlet"

    override fun process(request: HttpServletRequest, response: HttpServletResponse) {
        val uri = request.requestURI
        val servletName = uri.substring(uri.lastIndexOf("/") + 1)
        var loader: URLClassLoader? = null
        try {
            // 创建类加载器
            val urls = arrayOfNulls<URL>(1)
            val streamHandler: URLStreamHandler? = null
            val classPath = File(WEB_ROOT)
            // class的存放目录或JAR文件的路径(此处为目录)
            val repository = URL("file", null,
                    classPath.canonicalPath + File.separator).toString()
            urls[0] = URL(null, repository, streamHandler)
            loader = URLClassLoader(urls)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var myClass: Class<*>? = null
        try {
            myClass = loader!!.loadClass(servletName)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        var servlet: Servlet? = null

        try {
            servlet = myClass!!.newInstance() as Servlet
            servlet.service(request, response)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}