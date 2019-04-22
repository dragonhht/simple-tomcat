package com.github.dragonhht.classloader

import java.io.File
import java.net.URL
import java.net.URLClassLoader

/**
 * 继承实现URLClassLoader.
 *
 * @author: huang
 * @Date: 2019-4-22
 */
class MyURLClassLoader constructor(var url: Array<URL>): URLClassLoader(url) {

    var classPaths = mutableListOf<String>()

    init {
        url.forEach {
            classPaths.add(it.file)
        }
    }

    constructor(): this(arrayOf())
    constructor(file: File): this(arrayOf(file.toURI().toURL()))
    constructor(filePath: String): this(arrayOf(File(filePath).toURI().toURL()))


    /**
     * 将指定文件添加到URLClassLoader中
     */
    fun addFile(file: File) {
        this.addURL(file.toURI().toURL())
    }

    /**
     * 递归遍历指定文件夹，加载class和jar
     */
    fun loadClassByPath(file: File) {
        if (file.isDirectory) {
            this.addFile(file)
            file.listFiles().forEach {
                loadClassByPath(it)
            }
        } else {
            if (file.name.endsWith(FileSuffix.JAR_SUFFIX)) {
                this.addFile(file)
            }
        }
    }

    fun loadClassByPath(path: String) {
        loadClassByPath(File(path))
    }

    fun loadClassByPath() {
        if (classPaths === null || classPaths.size < 1) {
            throw RuntimeException("classPaths 不能为空!")
        }
        classPaths.forEach { path ->
            loadClassByPath(path)
        }
    }
}