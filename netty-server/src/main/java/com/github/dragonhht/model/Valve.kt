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
    private var className: String? = null
    @XStreamAsAttribute
    private var directory: String? = null
    @XStreamAsAttribute
    private var prefix: String? = null
    @XStreamAsAttribute
    private var suffix: String? = null
    @XStreamAsAttribute
    private var pattern: String? = null

    override fun toString(): String {
        return "Valve(className=$className, directory=$directory, prefix=$prefix, suffix=$suffix, pattern=$pattern)"
    }


}