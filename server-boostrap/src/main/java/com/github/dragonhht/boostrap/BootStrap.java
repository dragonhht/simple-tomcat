package com.github.dragonhht.boostrap;


import com.github.dragonhht.boostrap.connector.HttpConnector;

/**
 * 启动类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public final class BootStrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }

}
