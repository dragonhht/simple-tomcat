package com.github.dragonhht.utils

import com.sun.javafx.application.ParametersImpl.getParameters
import org.slf4j.LoggerFactory
import java.lang.reflect.AccessibleObject.setAccessible
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Parameter


/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-18
 */
enum class ReflectionUtil {
    INSTANCE;

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 创建实例.
     * @param cls
     * @return
     */
    fun<T: Any> newInstance(cls: Class<T>): T {
        var instance: T
        try {
            instance = cls.newInstance()
        } catch (e: IllegalAccessException) {
            throw RuntimeException("${cls.name} 类查询不到默认构造器")
            log.error("类实例化失败", e)
        }
        return instance
    }

    /**
     * 调用方法.
     * @param obj 对象
     * @param method 方法
     * @param args 参数
     * @return
     */
    fun invokeMethod(obj: Any, method: Method, vararg args: Any): Any? {
        var result: Any? = null
        try {
            method.setAccessible(true)
            result = method.invoke(obj, args)
        } catch (e: IllegalAccessException) {
            log.error("方法调用失败", e)
        } catch (e: InvocationTargetException) {
            log.error("方法调用失败", e)
        }

        return result
    }

    /**
     * 设置成员属性的值.
     * @param obj 对象
     * @param field 属性
     * @param value 值
     */
    fun setField(obj: Any, field: Field, value: Any?) {
        try {
            field.setAccessible(true)
            field.set(obj, value)
        } catch (e: IllegalAccessException) {
            log.error("设置属性值失败", e)
        }

    }

    fun getFieldVlaue(obj: Any, field: Field): Any? {
        try {
            field.setAccessible(true)
            return field.get(obj)
        } catch (e: IllegalAccessException) {
            log.error("设置属性值失败", e)
        }
        return null
    }

    /**
     * 设置成员属性的值.
     * @param obj 对象
     * @param field 属性名
     * @param value 值
     */
    fun setField(obj: Any, fieldName: String, value: Any?) {
        val field = obj::class.java.getDeclaredField(fieldName)
        setField(obj, field, value)
    }

    /**
     * 获取方法的参数信息.
     * @param method 方法
     * @return 参数信息
     */
    fun getMethodParameters(method: Method): Array<Parameter> {
        val parameter = method.getParameters()[0]
        return method.getParameters()
    }
}