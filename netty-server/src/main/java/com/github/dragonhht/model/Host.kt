package com.github.dragonhht.model

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class Host {
    private var name: String? = null
    private var appBase: String? = null
    private var unpackWARs: String? = null
    private var autoDeploy: Boolean = false

    private var valves: MutableList<Valve> = mutableListOf()
    private var contexts: MutableList<Context> = mutableListOf()
}