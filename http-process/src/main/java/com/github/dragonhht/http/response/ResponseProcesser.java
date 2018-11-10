package com.github.dragonhht.http.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 用于获取Response.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public class ResponseProcesser {
    private static ResponseProcesser ourInstance = new ResponseProcesser();

    public static ResponseProcesser getInstance() {
        return ourInstance;
    }

    private ResponseProcesser() {
    }

    /**
     * 获取Response.
     * @param output 输出流
     * @param request 填充好的Request.
     * @return HttpServletResponse
     */
    public HttpServletResponse process(OutputStream output, HttpServletRequest request) {
        Response response = new Response(output);
        response.setRequest(request);
        return new ResponseFacade(response);
    }

}
