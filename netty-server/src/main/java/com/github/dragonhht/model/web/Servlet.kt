package com.github.dragonhht.model.web

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-22
 */
class Servlet {
    var servletClass: String? = null
    var initParams: MutableList<InitParam> = mutableListOf()
    var loadOnStartup: String? = null

    override fun toString(): String {
        return "Servlet(servletClass=$servletClass, initParams=$initParams, loadOnStartup=$loadOnStartup)"
    }


}