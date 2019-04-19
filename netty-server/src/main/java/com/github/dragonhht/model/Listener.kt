package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Listener")
class Listener {

    @XStreamAsAttribute
    var className: String? = null

    override fun toString(): String {
        return "Listener(className=$className)"
    }


}