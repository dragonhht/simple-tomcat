package com.github.dragonhht.boostrap.connector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 连接器.
 * 负责创建ServerSocket，并等待Http请求
 *
 * @author: huang
 * Date: 2018/4/20
 */
@Slf4j
public class HttpConnector implements Runnable {

    private boolean isStoped;
    /** 请求协议. */
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    /**
     * 服务接收请求线程.
     */
    @Override
    public void run() {
        ServerSocket server = null;
        int port = 8080;
        try {
            server = new ServerSocket(port);
            log.info("服务启动，端口为： " + port);
        } catch (IOException e) {
            log.info("服务启动出错: ", e);
            System.exit(1);
        }

        while (!isStoped) {
            Socket client;
            try {
                client = server.accept();
            } catch (IOException e) {
                log.info("请求出错: ", e);
               continue;
            }
            HttpProcesser processer = new HttpProcesser();
            processer.process(client);
        }
    }

    /**
     * 服务启动.
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
