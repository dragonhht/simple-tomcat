package com.github.dragonhht.boostrap.container;

import com.github.dragonhht.boostrap.connector.HttpProcesser;

import java.net.Socket;

/**
 * 处理具体的请求.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public class Container implements Runnable {

    private Socket client;

    @Override
    public void run() {
        HttpProcesser processer = new HttpProcesser();
        processer.process(client);
    }

    /**
     * 启动请求处理.
     * @param client 客户端连接
     */
    public void start(Socket client) {
        this.client = client;
        new Thread(this).start();
    }

    /**
     * 停止请求处理.
     */
    public void stop() {
        // TODO 停止
    }
}
