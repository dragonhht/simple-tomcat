package com.github.dragonhht.utils

import com.esotericsoftware.reflectasm.FieldAccess
import com.esotericsoftware.reflectasm.MethodAccess

/**
 * 使用ReflectASM构建的反射工具类.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
enum class ReflectASMUtil {

    INSTANCE;

    /**
     * 调用方法
     * @param methodName: 方法名
     * @param obj: 实例
     * @param value: 设置的值
     * @return 方法的返回结果
     */
    fun invokeMethod(methodName: String, obj: Any, value: Any?): Any {
        val access = MethodAccess.get(obj::class.java)
        return access.invoke(obj, methodName, value)
    }

    /**
     * 设置属性值.
     * @param fieldName: 属性名
     * @param obj: 实例
     * @param value: 设置的值
     */
    fun setField(fieldName: String, obj: Any, value: Any) {
        val access = FieldAccess.get(obj::class.java)
        access.set(obj, fieldName, value)
    }

}