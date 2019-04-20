package com.github.dragonhht.classloader

import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URLClassLoader
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.FileChannel
import java.nio.channels.WritableByteChannel
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * 自定义类加载器.
 *
 * @author: huang
 * @Date: 2019-4-19
 */
class MyAppClassLoader: ClassLoader {

    private val log = LoggerFactory.getLogger(this::class.java)

    private val CLASS_SUFFIX = ".class"
    private val JAR_SUFFIX = ".jar"

    private var classPath: String

    private val classMap: MutableMap<String, Class<*>> = mutableMapOf()

    constructor(classPath: String): super() {
        this.classPath = classPath
    }

    constructor(classPath: String, parent: ClassLoader): super(parent) {
        this.classPath = classPath
    }

    /**
     * 记载classPath下的指定class.
     */
    override public fun findClass(className: String): Class<*>? {
        var filePath = className
        var className = className
        if (filePath.endsWith(CLASS_SUFFIX)) {
            filePath = filePath.substringBeforeLast(CLASS_SUFFIX)
            className = filePath
        }

        if (classMap[className] != null) {
            return classMap[className]
        }

        filePath = filePath.replace('.', File.separatorChar)
        filePath = classPath + File.separatorChar + filePath + CLASS_SUFFIX
        try {
            val bytes = getClassBytes(File(filePath))
            return defineClass(className, bytes, 0, bytes.size)
        } catch (e: Exception) {
            //log.error("自定义类加载器加载 $className 异常: $e")
        }
        return super.findClass(className)
    }

    /**
     * 加载classPath下的所有class文件.
     */
    fun loadClassByClassPath() {
        loadClassByClassPath(File(classPath))
    }

    /**
     * 递归遍历指定文件夹，加载class
     */
    private fun loadClassByClassPath(file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach {
                loadClassByClassPath(it)
            }
        } else {
            if (file.name.endsWith(CLASS_SUFFIX)) {
                var className = file.absolutePath
                className = className.substringAfter(classPath + File.separatorChar)
                className = className.substringBeforeLast(CLASS_SUFFIX)
                className = className.replace(File.separatorChar, '.')
                findClass(className)
            }
            if (file.name.endsWith(JAR_SUFFIX)) {
                /*val jarFile = JarFile(file)
                loadClassByJar(jarFile, file.absolutePath)*/
                loadClassByJar(file)
            }
        }
    }

    /**
     * 加载jar中的class文件到jvm
     */
    fun loadClassByJar(file: File) {
        val jar = JarFile(file)
        val urlClassLoader = URLClassLoader(arrayOf(file.toURI().toURL()))
        val jarEntries = jar.entries()
        while (jarEntries.hasMoreElements()) {
            val jarEntry = jarEntries.nextElement()
            val name = jarEntry.name
            if (name.endsWith(CLASS_SUFFIX)) {
                var clazz = name.substringBeforeLast(CLASS_SUFFIX).replace("/", ".")
                try {
                    this.findClass(clazz)
                    continue
                } catch (e: Exception) {}
                // 加载jar中的的class
                val clss = urlClassLoader.loadClass(clazz)
                classMap[clazz] = clss
            }
        }
    }

    /**
     * 加载jar中的class文件到jvm
     */
    fun loadClassByJar(jar: JarFile, jarPath: String) {
        val jarEntries = jar.entries()
        while (jarEntries.hasMoreElements()) {
            val jarEntry = jarEntries.nextElement()
            val name = jarEntry.name
            if (name.endsWith(CLASS_SUFFIX)) {
                var clazz = name.substringBeforeLast(CLASS_SUFFIX).replace("/", ".")
                try {
                    this.findClass(clazz)
                    continue
                } catch (e: Exception) {}
                // 加载jar中的的class
                val bytes = getClassByteByJar(jar, jarEntry)
                println(clazz)
                this.defineClass(clazz, bytes, 0, bytes.size)

                /*val s = URLClassLoader(arrayOf(URL("file", null, jarPath))).loadClass(clazz)
                println(clazz)
                Class.forName(clazz, true, s)*/
            }
        }
    }

    /**
     * 读取jar包中的class文件
     */
    private fun getClassByteByJar(jar: JarFile, jarEntry: JarEntry): ByteArray {
        return getClassBytes(jar.getInputStream(jarEntry))
    }

    private fun getClassBytes(input: InputStream): ByteArray {
        val bufInput = input.buffered()
        val baos: ByteArrayOutputStream = ByteArrayOutputStream()
        try {
            val bytes = ByteArray(1024)
            var len = -1
            while (true) {
                len = bufInput.read(bytes)
                (if (len != -1) {} else {break})
                baos.write(bytes, 0, len)
            }
            return baos.toByteArray()
        } finally {
            baos?.close()
            bufInput?.close()
            input?.close()
        }
    }

    /**
     * 获取class文件二进制流
     */
    private fun getClassBytes(file: File): ByteArray {
        // 使用NIO
        var fis: FileInputStream = file.inputStream()
        var fc: FileChannel? = null
        var baos: ByteArrayOutputStream? = null
        var wbc: WritableByteChannel? = null
        var bb: ByteBuffer? = null
        try {
            val fc = fis.channel
            val baos = ByteArrayOutputStream()
            val wbc = Channels.newChannel(baos)
            val bb = ByteBuffer.allocate(1024)

            while (fc.read(bb) != -1) {
                bb.flip()
                wbc.write(bb)
                bb.clear()
            }
            val result = baos.toByteArray()
            return result
        } finally {
            wbc?.close()
            fc?.close()
            baos?.close()
            fis?.close()
        }
    }
}

fun main(args: Array<String>) {
    //val classPath = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\WebRoot\\app"
    val className = "com.github.dragonhht.test.Test.class"
    val classPath = "D:/application/apache-jmeter-5.0/lib"
    MyAppClassLoader(classPath).loadClassByClassPath()
    //val className = "com.github.dragonhht.test.servlet.TestServlet.class"
    /*val clzz = MyAppClassLoader(classPath).loadClass(className)
    println(clzz.name)*/
    /*val clzz = URLClassLoader(arrayOf(URL("file", null, classPath))).loadClass("net.minidev.asm.BasicFiledFilter")
    println(clzz.name)*/
}