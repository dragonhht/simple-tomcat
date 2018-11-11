package com.github.dragonhht.boostrap.service;

import com.github.dragonhht.boostrap.component.ServiceLifecycle;
import com.github.dragonhht.boostrap.connector.ComponentConnector;
import com.github.dragonhht.boostrap.container.ComponentEngine;

/**
 * Service接口.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public interface ComponentService extends ServiceLifecycle {

    /**
     * 添加Connector.
     * @param connector
     */
    void addConnector(ComponentConnector connector);

    void setEngine(ComponentEngine componentEngine);

    ComponentEngine getEngine();

}
