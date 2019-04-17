package com.github.dragonhht.process.handler

import com.github.dragonhht.handler.RequestHandler
import com.github.dragonhht.http.servlet.Request
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-16
 */
class HttpServerInitializer: ChannelInitializer<SocketChannel>() {

    @Throws(Exception::class)
    override fun initChannel(socketChannel: SocketChannel) {
        // 获取管道并在管道中追加handler
        socketChannel.pipeline().addLast("http-codec", HttpServerCodec())
        socketChannel.pipeline().addLast("http-aggregator", HttpObjectAggregator(6555))
        socketChannel.pipeline().addLast("custom-request", RequestHandler())
    }
}