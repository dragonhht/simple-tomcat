package com.github.dragonhht.boostrap.component;

/**
 * 组件的生命周期.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public interface ServiceLifecycle {

    /**
     * 初始化.
     */
    void init();
    /**
     * 启动组件.
     */
    void start();

    /**
     * 停止组件.
     */
    void stop();

    /**
     * 销毁组件.
     */
    void destroy();

}
