package com.github.dragonhht.boostrap;


import com.github.dragonhht.boostrap.connector.Connector;
import com.github.dragonhht.boostrap.container.Container;

import java.util.Scanner;
import java.util.Vector;

/**
 * 启动类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public final class Server {

    private final Vector<Connector> connectors = new Vector<>();

    /**
     * 启动服务.
     */
    public void start(int port) {
        Connector connector = new Connector(port);
        connectors.add(connector);
        connector.start();
    }

    /**
     * 停止服务.
     */
    public void stop() {
        for (Connector connector : connectors) {
            connector.stop();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        int port = 8080;
        for (int i = 0; i < 5; i++) {
            server.start(port++);
        }

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        System.out.println(line);
        if ("stop".equals(line)) {
            System.out.println("stop");
            server.stop();
        }
    }
}
