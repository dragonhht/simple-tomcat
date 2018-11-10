package com.github.dragonhht.boostrap.Processor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求处理接口.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public interface HttpDoProcessor {

    void process(HttpServletRequest request, HttpServletResponse response);

}
