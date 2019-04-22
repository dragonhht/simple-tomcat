package com.github.dragonhht.model.web

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-22
 */
class WebApp {
    var displayName: String? = null
    var contextParam: MutableList<ContextParam> = mutableListOf()
    var filters: MutableMap<FilterMap, Filter> = mutableMapOf()
    var listenerClass: MutableList<String> = mutableListOf()
    var servlets: MutableMap<ServletMap, Servlet> = mutableMapOf()

    override fun toString(): String {
        return "WebApp(displayName=$displayName, contextParam=$contextParam, filters=$filters, listenerClass=$listenerClass, servlets=$servlets)"
    }


}