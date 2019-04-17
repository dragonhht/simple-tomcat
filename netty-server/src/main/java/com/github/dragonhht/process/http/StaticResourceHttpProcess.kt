package com.github.dragonhht.process.http

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 静态资源请求处理.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class StaticResourceHttpProcess: HttpProcess {

    /** 静态资源文件存放路径.  */
    private val WEB_ROOT = "D:/my_work_spance/idea_workspance/simple-tomcat/WebRoot/"

    override fun process(request: HttpServletRequest, response: HttpServletResponse) {
        val uri = request.requestURI
        senStaticResource(uri, response)
    }

    /**
     * 发送静态资源.
     */
    private fun senStaticResource(path: String, response: HttpServletResponse) {
        try {
            val out = response.outputStream
            getFile(path, out)
            out.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 获取静态文件.
     */
    private fun getFile(path: String?, out: ServletOutputStream) {
        var path: String? = path ?: return
        path = WEB_ROOT + path!!
        try {
            try {
                val file = File(path)
                out.write(file.readBytes())
            } catch (e: FileNotFoundException) {
                path = "$WEB_ROOT/html/error/404.html"
                val file = File(path)
                out.write(file.readBytes())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}