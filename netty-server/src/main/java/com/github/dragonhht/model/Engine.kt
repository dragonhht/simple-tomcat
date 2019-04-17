package com.github.dragonhht.model

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class Engine {
    private var name: String = "netty-tomcat"
    private var defaultHost: String = "localhost"
    private var realms: MutableList<Realm> = mutableListOf()
    private var hosts: MutableList<Host> = mutableListOf()
}