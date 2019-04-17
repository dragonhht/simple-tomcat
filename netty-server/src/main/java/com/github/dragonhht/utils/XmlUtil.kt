package com.github.dragonhht.utils

import org.dom4j.Attribute
import org.dom4j.Element
import org.dom4j.io.SAXReader
import kotlin.reflect.KClass

/**
 * xml文件处理工具类.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
enum class XmlUtil {

    INSTANCE;

    // 创建SAXReader对象
    private val saxReader = SAXReader()

    fun parse(filePath: String, clazz: KClass<out Any>) {
        val document = saxReader.read(filePath)
        // 获取文档根节点
        val root = document.rootElement
        root.elements().forEach {
            val element = it as Element
            // 获取所有参数
            element.attributes().forEach {  attribute ->
                println((attribute as Attribute).value)
            }
        }
    }

}