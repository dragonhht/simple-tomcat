package com.github.dragonhht.boostrap.container;

import java.net.Socket;

/**
 * 描述.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public interface ComponentEngine {
    /**
     * 初始化.
     */
    void init();
    void start(Socket client);
    void stop();
    /**
     * 销毁组件.
     */
    void destroy();
}
