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

    override fun equals(other: Any?): Boolean {
        if (other is ServletMap) {
            if (this.servletName == other.servletName) {
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        var result = 0x002
        result = result * 31 + servletName.hashCode()
        return result
    }

}