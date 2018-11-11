package com.github.dragonhht.boostrap.connector;

import com.github.dragonhht.boostrap.container.ComponentEngine;
import com.github.dragonhht.boostrap.service.ComponentService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * 连接器.
 * 负责创建ServerSocket，并等待Http请求
 *
 * @author: huang
 * Date: 2018/4/20
 */
@Slf4j
public class Connector implements Runnable, ComponentConnector {

    private Vector<ComponentEngine> componentEngines = new Vector<>();
    private ComponentService service;

    private boolean isStoped;
    private int port = 8080;
    /** 请求协议. */
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    public Connector(int port) {
        this.port = port;
    }

    /**
     * 服务接收请求线程.
     */
    @Override
    public void run() {
        ServerSocket server = null;
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
                if (!isStoped) {
                    ComponentEngine engine = this.service.getEngine();
                    engine.start(client);
                }
            } catch (IOException e) {
                log.info("请求出错: ", e);
            }
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("服务器已关闭");
    }

    @Override
    public void init() {

    }

    /**
     * 服务启动.
     */
    @Override
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * 停止监听客户端.
     */
    @Override
    public void stop() {
        this.isStoped = true;
        try (Socket socket = new Socket("localhost", port)) {
            for (ComponentEngine componentEngine : componentEngines) {
                componentEngine.stop();
            }
        } catch (IOException e) {
            log.error("停止请求监听服务出错", e);
        }
    }

    @Override
    public void setService(ComponentService service) {
        this.service = service;
    }

    @Override
    public ComponentService getService() {
        return this.service;
    }

    @Override
    public void destroy() {

    }
}
