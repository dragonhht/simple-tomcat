package com.github.dragonhht.handler

import com.github.dragonhht.http.servlet.Request
import com.github.dragonhht.http.servlet.RequestFacade
import com.github.dragonhht.http.servlet.Response
import com.github.dragonhht.http.servlet.ResponseFacade
import com.github.dragonhht.process.http.ServletHttpProcess
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.*
import io.netty.handler.codec.http.multipart.Attribute
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder
import io.netty.handler.codec.http.multipart.InterfaceHttpData
import io.netty.util.CharsetUtil
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


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

        val request = Request()
        val responseStream = ByteArrayOutputStream()
        val response = Response(responseStream)
        // 设置request的输入流
        setRequestInputStream(req, request)
        parseRequest(req, request)

        // 门面类
        val requestFacade = RequestFacade(request)
        val responseFacade = ResponseFacade(response)

        // 调用具体的处理逻辑
        ServletHttpProcess().process(requestFacade, responseFacade)

        // 创建http响应
        val resp = DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(responseStream.toString(), CharsetUtil.UTF_8))
        // 设置头信息
        resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8")
        // 将数据返回客户端
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE)
    }

    /**
     * 设置Request的headers数据.
     */
    private fun parseHeaders(req: FullHttpRequest, request: Request) {
        // 设置头信息
        val headers = req.headers()
        headers.forEach { request.addHeader(it.key, it.value) }
    }

    /**
     * 解析uri中的参数.
     */
    private fun parseUriQueryParame(req: FullHttpRequest, request: Request) {
        // 设置参数
        val decoder = QueryStringDecoder(req.uri())
        decoder.parameters().forEach{ request.setParameter(it.key, it.value.toTypedArray())}
    }

    /**
     * 将GET请求的数据放入Request中.
     */
    private fun parseGetRequest(req: FullHttpRequest, request: Request) {
        // 设置GET方法参数
        parseHeaders(req, request)
        parseGetRequest(req, request)
    }

    /**
     * 设置Request对象的InputStream.
     */
    private fun setRequestInputStream(req: FullHttpRequest, request: Request) {
        val content = req.content()
        request.setInputStream(ByteArrayInputStream(content.toString(Charsets.UTF_8).toByteArray(Charsets.UTF_8)))
    }

    /**
     * 将POST请求的数据放入Request中.
     */
    private fun parseRequest(req: FullHttpRequest, request: Request) {
        request.requestURI = req.uri()
        request.method = req.method().name()

        parseHeaders(req, request)
        parseUriQueryParame(req, request)
        val decoder = HttpPostRequestDecoder(req)
        val dataList = decoder.bodyHttpDatas
        for (data in dataList) {
            if (data.httpDataType == InterfaceHttpData.HttpDataType.Attribute) {
                val attribute = data as Attribute
                request.setParameter(attribute.name, attribute.value)
            }
            if (data.httpDataType == InterfaceHttpData.HttpDataType.FileUpload) {
                // TODO 当上传文件时
            }
        }
    }

}