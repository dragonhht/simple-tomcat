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

    var equalsField = MapEquals.NAME

    override fun toString(): String {
        return "ServletMap(servletName=$servletName, urlPattern=$urlPattern)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is ServletMap) {
            if (other.equalsField === MapEquals.NAME) {
                if (this.servletName == other.servletName) {
                    return true
                }
            }
            if (other.equalsField === MapEquals.URL) {
                if (this.urlPattern == other.urlPattern) {
                    return true
                }
            }
            if (other.equalsField === MapEquals.ALL) {
                if (this.urlPattern == other.urlPattern && this.servletName == other.servletName) {
                    return true
                }
            }
        }
        return false
    }

    override fun hashCode(): Int {
        var result = 0x002
        if (this.equalsField === MapEquals.NAME) {
            result = result * 31 + servletName.hashCode()
        }
        if (this.equalsField === MapEquals.URL) {
            result = result * 31 + urlPattern.hashCode()
        }
        if (this.equalsField === MapEquals.ALL) {
            result = result * 31 + servletName.hashCode()
            result = result * 31 + urlPattern.hashCode()
        }
        return result
    }

}