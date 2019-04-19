package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamImplicit

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Host")
class Host {
    @XStreamAsAttribute
    var name: String? = null
    @XStreamAsAttribute
    var appBase: String? = null
    @XStreamAsAttribute
    var unpackWARs: String? = null
    @XStreamAsAttribute
    var autoDeploy: Boolean = false
    @XStreamImplicit(itemFieldName = "Valve")
    var valves: MutableList<Valve> = mutableListOf()
    @XStreamImplicit(itemFieldName = "Context")
    var contexts: MutableList<Context> = mutableListOf()

    override fun toString(): String {
        return "Host(name=$name, appBase=$appBase, unpackWARs=$unpackWARs, autoDeploy=$autoDeploy, valves=\n$valves, contexts=\n$contexts)"
    }


}