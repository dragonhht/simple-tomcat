package com.github.dragonhht.model

/**
 * Server.xml配置的对应实体.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class Server {

    private var port: Int = 0
    var shutdown: String? = null
    private var listeners: MutableList<Listener> = mutableListOf()

    override fun toString(): String {
        return "Server(port=$port, shutdown=$shutdown, listeners=$listeners)"
    }
}