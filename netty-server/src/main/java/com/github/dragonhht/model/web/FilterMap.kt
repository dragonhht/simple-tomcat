package com.github.dragonhht.model.web

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-22
 */
class FilterMap {
    var filterName: String? = null
    var urlPattern: String? = null

    override fun equals(other: Any?): Boolean {
        if (other is FilterMap) {
            if (this.filterName == other.filterName) {
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        var result = 0x001
        result = result * 31 + filterName.hashCode()
        //result = result * 31 + urlPattern.hashCode()
        return result
    }

    override fun toString(): String {
        return "FilterMap(filterName=$filterName, urlPattern=$urlPattern)"
    }
}