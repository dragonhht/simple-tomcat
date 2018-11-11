package com.github.dragonhht.boostrap.service;


import com.github.dragonhht.boostrap.connector.Connector;
import com.github.dragonhht.boostrap.container.Container;

import java.util.Vector;

/**
 * 服务类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public final class Service implements ComponentService {

    private final Vector<Connector> connectors = new Vector<>();
    private Container container;

    @Override
    public void init() {

    }

    /**
     * 启动服务.
     */
    public void start() {
        for (Connector connector : connectors) {
            connector.start();
        }
    }

    /**
     * 停止服务.
     */
    public void stop() {
        for (Connector connector : connectors) {
            connector.stop();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void addConnector(Connector connector) {
        this.connectors.add(connector);
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
