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
@XStreamAlias("Engine")
class Engine {
    @XStreamAsAttribute
    private var name: String = "netty-tomcat"
    @XStreamAsAttribute
    private var defaultHost: String = "localhost"
    @XStreamImplicit(itemFieldName = "Realm")
    private var realms: MutableList<Realm> = mutableListOf()
    @XStreamImplicit(itemFieldName = "Host")
    private var hosts: MutableList<Host> = mutableListOf()

    override fun toString(): String {
        return "Engine(name='$name', defaultHost='$defaultHost', realms=\n$realms, hosts=\n$hosts)"
    }


}