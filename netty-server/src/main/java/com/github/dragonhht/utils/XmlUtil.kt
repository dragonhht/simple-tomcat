package com.github.dragonhht.utils

import com.github.dragonhht.model.Server
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import java.io.File

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-18
 */
enum class XmlUtil {
    INSTANCE;

    /**
     * 将xml文件解析成Java对象
     * @param clazz: Java类型
     * @param filePath: xml文件路径
     */
    fun<T> parse(clazz: Class<T>, filePath: String): T {
        val xstream = XStream(DomDriver());//创建Xstram对象
        xstream.autodetectAnnotations(true)
        xstream.processAnnotations(clazz)
        return xstream.fromXML(File(filePath)) as T
    }

}