package hht.dragon.server.request;

import java.util.Set;

/**
 * 简易HttpServletRequest接口.
 *
 * @author: huang
 * Date: 18-1-18
 */
public interface SimpleServletRequest {

    /**
     * 获取请求uri.
     */
    String getRequestURI();
    /**
     *  获取请求类型.
     */
    String getRequestType();

    /**
     * 获取相应头信息值.
     * @param name
     * @return
     */
    String getHeader(String name);

    /**
     * 获取参数.
     * @param name
     * @return
     */
    String getParameter(String name);

    /**
     * 获取啊所有参数的键.
     * @return 参数键的集合
     */
    Set<String> getParameterNames();
}
