package com.github.dragonhht.model.web

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-22
 */
class ServletMap {
    var servletName: String? = null
    var urlPattern: String? = null

    override fun toString(): String {
        return "ServletMap(servletName=$servletName, urlPattern=$urlPattern)"
    }


}