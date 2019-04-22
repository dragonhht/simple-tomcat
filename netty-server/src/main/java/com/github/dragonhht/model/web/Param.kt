package com.github.dragonhht.model.web

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-22
 */
abstract class Param {
    var paramName: String? = null
    var paramValue: String? = null

    override fun toString(): String {
        return "Param(paramName=$paramName, paramValue=$paramValue)"
    }
}