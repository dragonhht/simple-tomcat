package com.github.dragonhht.utils.helper

import com.github.dragonhht.model.web.*
import org.dom4j.DocumentException
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.slf4j.LoggerFactory
import java.io.File


/**
 * 读取web.xml文件的助手类.
 *
 * @author: huang
 * @Date: 2019-4-22
 */
// TODO 待优化
object WebXmlReaderHelper {
    private val log = LoggerFactory.getLogger(this::class.java)

    // 创建SAXReader对象
    var saxReader = SAXReader()

    private val filterElements = listOf("filter", "filter-mapping")
    private val servletElements = listOf("servlet", "servlet-mapping")

    fun read(filePath: String): WebApp {
        val webApp = WebApp()
        try {
            val dom = saxReader.read(File(filePath))
            val root = dom.rootElement
            root.elements().forEach { ele ->
                val element = ele as Element
                val name = element.name
                when(name) {
                    "display-name" -> webApp.displayName = name
                    "context-param" -> {
                        val contextParam = ContextParam()
                        parseParam(element, contextParam)
                        webApp.contextParam.add(contextParam)
                    }
                    in filterElements -> parseFilter(element, webApp)
                    "listener" -> parseListener(element, webApp)
                    in servletElements -> parseServlet(element, webApp)
                }
            }
        } catch (e: DocumentException) {
            log.error("解析 $filePath 文件出错: ", e)
        }
        return webApp
    }

    /**
     * 解析参数信息
     */
    private fun parseParam(ele: Element, param: Param) {
        ele.elements().forEach {
            val element = it as Element
            val name = element.name
            when(name) {
                "param-name" -> param.paramName = element.textTrim
                "param-value" -> param.paramValue = element.textTrim
            }
        }
    }

    private fun parseFilter(ele: Element, webApp: WebApp) {
        val map = FilterMap()
        var filter = Filter()
        ele.elements().forEach {
            val element = it as Element
            val name = element.name
            when(name) {
                "filter-name" -> {
                    map.filterName = element.textTrim
                    val f = webApp.filters[map]
                    if (f !== null) filter = f
                    webApp.filters[map] = filter
                }
                "url-pattern" -> {
                    webApp.filters.remove(map)
                    map.urlPattern = element.textTrim
                    webApp.filters[map] = filter
                }
                "filter-class" -> filter.filterClass = element.textTrim
                "init-param" -> {
                    val param = InitParam()
                    parseParam(element, param)
                    filter.initParams.add(param)
                }
            }
        }
    }

    private fun parseListener(ele: Element, webApp: WebApp) {
        val listener = Listener()
        ele.elements().forEach {
            val element = it as Element
            val name = element.name
            // TODO 还有其他标签
            when(name) {
                "listener-class" -> listener.listenerClass = element.textTrim
            }
            webApp.listeners.add(listener)
        }
    }

    private fun parseServlet(ele: Element, webApp: WebApp) {
        val map = ServletMap()
        var servlet = Servlet()
        ele.elements().forEach {
            val element = it as Element
            val name = element.name
            when(name) {
                "servlet-name" -> {
                    map.servletName = element.textTrim
                    val s = webApp.servlets[map]
                    if (s !== null) servlet = s
                    webApp.servlets[map] = servlet
                }
                "url-pattern" -> {
                    webApp.servlets.remove(map)
                    map.urlPattern = element.textTrim
                    webApp.servlets[map] = servlet
                }
                "servlet-class" -> servlet.servletClass = element.textTrim
                "init-param" -> {
                    val param = InitParam()
                    parseParam(element, param)
                    servlet.initParams.add(param)
                }
                "load-on-startup" -> servlet.loadOnStartup = element.textTrim
            }
        }
    }

}

fun main(args: Array<String>) {
    val path = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\netty-server\\src\\main\\resources\\conf\\web.xml"
    val web = WebXmlReaderHelper.read(path)
    println(web)
}