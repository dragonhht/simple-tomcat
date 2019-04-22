package com.github.dragonhht.model.web

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-22
 */
class Filter {
    var filterClass: String? = null
    var initParams: MutableList<InitParam> = mutableListOf()

    override fun toString(): String {
        return "Filter(filterClass=$filterClass, initParams=$initParams)"
    }


}