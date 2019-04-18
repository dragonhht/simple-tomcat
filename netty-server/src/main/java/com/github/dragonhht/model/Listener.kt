package com.github.dragonhht.model

import com.github.dragonhht.utils.annotations.XmlModel
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
    private var className: String? = null

    override fun toString(): String {
        return "Listener(className=$className)"
    }


}