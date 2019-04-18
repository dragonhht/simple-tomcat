package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute

/**
 * docBase="${catalina.home}/../../ekp" path="/ekp" reloadable="false".
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Context")
class Context {
    @XStreamAsAttribute
    private var docBase: String? = null
    @XStreamAsAttribute
    private var path: String? = null

    override fun toString(): String {
        return "Context(docBase=$docBase, path=$path)"
    }


}