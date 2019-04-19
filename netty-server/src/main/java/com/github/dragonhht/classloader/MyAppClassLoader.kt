package com.github.dragonhht.classloader

import com.github.dragonhht.utils.ReflectionUtil
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.FileChannel
import java.nio.channels.WritableByteChannel
import javax.servlet.http.HttpServlet

/**
 * 自定义类加载器.
 *
 * @author: huang
 * @Date: 2019-4-19
 */
class MyAppClassLoader: ClassLoader {

    private val log = LoggerFactory.getLogger(this::class.java)

    private val CLASS_SUFFIX = ".class"

    private lateinit var classPath: String

    constructor(classPath: String): super() {
        this.classPath = classPath
    }

    constructor(parent: ClassLoader, classPath: String): super(parent) {
        this.classPath = classPath
    }

    /**
     * 记载classPath下的指定class.
     */
    override fun findClass(className: String): Class<*> {
        var filePath = className
        var className = className
        if (filePath.endsWith(".class")) {
            filePath = filePath.substringBeforeLast(".class")
            className = filePath
        }
        filePath = filePath.replace('.', File.separatorChar)
        filePath = classPath + File.separatorChar + filePath + CLASS_SUFFIX
        try {
            val bytes = getClassBytes(File(filePath))
            return defineClass(className, bytes, 0, bytes.size)
        } catch (e: Exception) {
            log.error("自定义类加载器加载 $className 异常: $e")
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
            }
        }
    }

    /**
     * 获取class文件字节流.
     * @param file: class文件
     */
    fun getClassBytes(file: File): ByteArray {
        // 使用NIO
        var fis: FileInputStream? = null
        var fc: FileChannel? = null
        var baos: ByteArrayOutputStream? = null
        var wbc: WritableByteChannel? = null
        var bb: ByteBuffer? = null
        try {
            val fis = file.inputStream()
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
    val classPath = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\WebRoot\\app"
    //MyAppClassLoader(classPath).loadClassByClassPath()
    val className = "com.github.dragonhht.test.servlet.TestServlet.class"
    val clzz = MyAppClassLoader(classPath).loadClass(className)
    val servlet = ReflectionUtil.INSTANCE.newInstance(clzz) as HttpServlet
    servlet.init()
}