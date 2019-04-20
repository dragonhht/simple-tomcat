package com.github.dragonhht.classloader

import java.util.jar.JarFile

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-20
 */
class ClassModel {
    var classBytes: ByteArray? = null
    var jar: JarFile? = null

    constructor() {

    }

    constructor(jar: JarFile, classBytes: ByteArray) {
        this.jar = jar
        this.classBytes = classBytes
    }
}