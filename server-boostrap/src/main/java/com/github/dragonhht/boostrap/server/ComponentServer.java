package com.github.dragonhht.boostrap.server;

import com.github.dragonhht.boostrap.component.ServiceLifecycle;
import com.github.dragonhht.boostrap.service.ComponentService;

/**
 * 服务.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public interface ComponentServer extends ServiceLifecycle {

    /**
     * 添加Service.
     * @param service service
     */
    void addService(ComponentService service);

}
