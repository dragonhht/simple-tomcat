package com.github.dragonhht.process.http

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Http请求处理接口.
 *
 * @author: huang
 * @Date: 2019-4-17
 */
interface HttpProcess {

    /**
     * 处理http请求.
     */
    fun process(request: HttpServletRequest, response: HttpServletResponse)

}