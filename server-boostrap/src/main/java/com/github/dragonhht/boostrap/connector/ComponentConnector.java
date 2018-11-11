package com.github.dragonhht.boostrap.connector;

import com.github.dragonhht.boostrap.component.ServiceLifecycle;
import com.github.dragonhht.boostrap.service.ComponentService;

/**
 * 描述.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public interface ComponentConnector extends ServiceLifecycle {
    void start();

    void stop();

    void setService(ComponentService service);
    ComponentService getService();
}
