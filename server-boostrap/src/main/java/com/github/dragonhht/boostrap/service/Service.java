package com.github.dragonhht.boostrap.service;


import com.github.dragonhht.boostrap.connector.ComponentConnector;
import com.github.dragonhht.boostrap.container.ComponentEngine;

import java.util.Vector;

/**
 * 服务类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public final class Service implements ComponentService {

    private final Vector<ComponentConnector> connectors = new Vector<>();
    private ComponentEngine engine;

    @Override
    public void init() {

    }

    /**
     * 启动服务.
     */
    public void start() {
        for (ComponentConnector connector : connectors) {
            connector.start();
        }
    }

    /**
     * 停止服务.
     */
    public void stop() {
        for (ComponentConnector connector : connectors) {
            connector.stop();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void addConnector(ComponentConnector connector) {
        connector.setService(this);
        this.connectors.add(connector);
    }

    @Override
    public void setEngine(ComponentEngine componentEngine) {
        this.engine = componentEngine;
    }

    @Override
    public ComponentEngine getEngine() {
        return this.engine;
    }
}
