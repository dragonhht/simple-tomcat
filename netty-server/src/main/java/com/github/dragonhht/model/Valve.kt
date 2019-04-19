package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Valve")
class Valve {
    @XStreamAsAttribute
    var className: String? = null
    @XStreamAsAttribute
    var directory: String? = null
    @XStreamAsAttribute
    var prefix: String? = null
    @XStreamAsAttribute
    var suffix: String? = null
    @XStreamAsAttribute
    var pattern: String? = null

    override fun toString(): String {
        return "Valve(className=$className, directory=$directory, prefix=$prefix, suffix=$suffix, pattern=$pattern)"
    }


}