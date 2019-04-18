package com.github.dragonhht.model

import com.github.dragonhht.utils.annotations.XmlAttribute
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamImplicit

/**
 * Server.xml配置的对应实体.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Server")
class Server {

    @XStreamAsAttribute
    private var port: String = ""

    @XStreamAsAttribute
    var shutdown: String? = null

    @XStreamImplicit(itemFieldName = "Listener")
    private var listeners: MutableList<Listener> = mutableListOf()

    @XStreamImplicit(itemFieldName = "Service")
    private var services: MutableList<Service> = mutableListOf()

    override fun toString(): String {
        return "Server(port='$port', shutdown=$shutdown, listeners=\n$listeners, services=\n$services)"
    }


}