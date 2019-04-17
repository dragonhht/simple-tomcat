package com.github.dragonhht.http.servlet

import java.util.HashMap

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
internal class ParameterMap<T, R> : HashMap<T, R?> {

    var isLocked = false

    constructor() : super() {}

    constructor(initialCapacity: Int) : super(initialCapacity) {}

    constructor(initialCapacity: Int, loadFactor: Float) : super(initialCapacity, loadFactor) {}

    constructor(map: Map<T, R?>) : super(map) {}

    override fun clear() {
        if (isLocked) throw IllegalStateException()
        super.clear()
    }

    override fun put(key: T, value: R?): R? {
        if (isLocked) throw IllegalStateException()
        return super.put(key, value)
    }

    override fun putAll(map: Map<out T, R?>) {
        if (isLocked) throw IllegalStateException()
        super.putAll(map)
    }

    override fun remove(key: T): R? {
        if (isLocked) throw IllegalStateException()
        return super.remove(key)
    }
}