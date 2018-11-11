package com.github.dragonhht.boostrap.service;

import com.github.dragonhht.boostrap.component.ServiceLifecycle;
import com.github.dragonhht.boostrap.connector.Connector;
import com.github.dragonhht.boostrap.container.Container;

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
    void addConnector(Connector connector);

    void setContainer(Container container);

}
