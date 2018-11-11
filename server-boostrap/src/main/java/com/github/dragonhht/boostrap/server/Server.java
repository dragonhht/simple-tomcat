package com.github.dragonhht.boostrap.server;

import com.github.dragonhht.boostrap.connector.ComponentConnector;
import com.github.dragonhht.boostrap.connector.Connector;
import com.github.dragonhht.boostrap.container.ComponentEngine;
import com.github.dragonhht.boostrap.container.Engine;
import com.github.dragonhht.boostrap.service.ComponentService;
import com.github.dragonhht.boostrap.service.Service;

import java.util.Vector;

/**
 * 服务.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public class Server implements ComponentServer {

    private final Vector<ComponentService> services = new Vector<>();

    @Override
    public void init() {

    }

    @Override
    public void start() {
        for (ComponentService service : services) {
            service.start();
        }
    }

    @Override
    public void stop() {
        for (ComponentService service : services) {
            service.stop();
        }
    }

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
        Server server = new Server();
        ComponentConnector connector = new Connector(8080);
        ComponentEngine engine = new Engine();
        Service service = new Service();
        service.setEngine(engine);
        service.addConnector(connector);
        server.addService(service);
        server.start();
    }

    @Override
    public void addService(ComponentService service) {
        this.services.add(service);
    }
}
