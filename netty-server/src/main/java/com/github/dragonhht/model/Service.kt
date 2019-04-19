package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Service")
class Service {
    @XStreamImplicit(itemFieldName = "Engine")
    var engines: MutableList<Engine> = mutableListOf()
    @XStreamImplicit(itemFieldName = "Connector")
    var connectors: MutableList<Connector> = mutableListOf()

    override fun toString(): String {
        return "Service(engines=\n$engines, connectors=\n$connectors)"
    }


}