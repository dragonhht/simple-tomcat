package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamImplicit

/**
 * Bootstrap.xml配置的对应实体.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Server")
class Server {

    @XStreamAsAttribute
    var port: Int = 0

    @XStreamAsAttribute
    var shutdown: String? = null

    @XStreamImplicit(itemFieldName = "Listener")
    var listeners: MutableList<Listener> = mutableListOf()

    @XStreamImplicit(itemFieldName = "Service")
    var services: MutableList<Service> = mutableListOf()

    override fun toString(): String {
        return "Bootstrap(port='$port', shutdown=$shutdown, listeners=\n$listeners, services=\n$services)"
    }


}