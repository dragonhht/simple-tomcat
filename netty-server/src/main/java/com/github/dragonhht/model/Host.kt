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
    private var name: String? = null
    @XStreamAsAttribute
    private var appBase: String? = null
    @XStreamAsAttribute
    private var unpackWARs: String? = null
    @XStreamAsAttribute
    private var autoDeploy: Boolean = false
    @XStreamImplicit(itemFieldName = "Valve")
    private var valves: MutableList<Valve> = mutableListOf()
    @XStreamImplicit(itemFieldName = "Context")
    private var contexts: MutableList<Context> = mutableListOf()

    override fun toString(): String {
        return "Host(name=$name, appBase=$appBase, unpackWARs=$unpackWARs, autoDeploy=$autoDeploy, valves=\n$valves, contexts=\n$contexts)"
    }


}