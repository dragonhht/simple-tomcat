package com.github.dragonhht.handler

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.*
import io.netty.util.CharsetUtil


/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-16
 */
class RequestHandler: SimpleChannelInboundHandler<FullHttpRequest>() {

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    @Throws(Exception::class)
    override fun channelRead0(ctx: ChannelHandlerContext, req: FullHttpRequest) {

        val buffer = req.content()
        println()

        // 请求的uri
        val uri = req.uri()
        // 请求的方法
        val methodName = req.method().name()
        val msg = "<html><head><title>发送的信息</title></head><body>请求的uri: $uri<br> 请求的方法: $methodName</body></html>"
        // 创建http响应
        val response = DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8))
        // 设置头信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8")
        // 将数据返回客户端
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE)
    }


}