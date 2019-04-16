package com.github.dragonhht

import com.github.dragonhht.process.handler.HttpServerInitializer
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel


/**
 * 服务.
 *
 * @author: huang
 * @Date: 2019-4-16
 */
class Server {

}

fun main(args: Array<String>) {
    val server = ServerBootstrap()
    val parentGroup = NioEventLoopGroup()
    val childGroup = NioEventLoopGroup()
    server.group(parentGroup, childGroup)
    server.channel(NioServerSocketChannel::class.java)
    server.childHandler(HttpServerInitializer());
    val future = server.bind(9999).sync()
    future.channel().closeFuture().sync()
}