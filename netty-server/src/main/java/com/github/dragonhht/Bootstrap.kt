package com.github.dragonhht

import com.github.dragonhht.classloader.MyURLClassLoader
import com.github.dragonhht.model.Server
import com.github.dragonhht.model.web.WebApp
import com.github.dragonhht.process.handler.HttpServerInitializer
import com.github.dragonhht.utils.XmlUtil
import com.github.dragonhht.utils.helper.WebXmlReaderHelper
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.slf4j.LoggerFactory


/**
 * 服务.
 *
 * @author: huang
 * @Date: 2019-4-16
 */
class Bootstrap {

    companion object {
        val classLoader = MyURLClassLoader()
        var webApp = WebApp()
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 初始化.
     */
    fun init(): Server {
        loadWebXml()
        loadClass()
        val filePath = "netty-server/src/main/resources/conf/server.xml"
        return XmlUtil.INSTANCE.parse(Server::class.java, filePath)
    }

    fun loadWebXml() {
        val path = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\netty-server\\src\\main\\resources\\conf\\web.xml"
        webApp = WebXmlReaderHelper.read(path)
    }

    /**
     * 加载外部应用class
     */
    private fun loadClass() {
        val classPath = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\WebRoot"
        classLoader.loadClassByPath(classPath)
    }

    /**
     * 启动.
     */
    fun start() {
        val server = init()
        server.services.forEach {  service ->
            service.connectors.forEach { connector ->
                if ("HTTP/1.1" == connector.protocol ) {
                    bootstrap(connector.port, connector.protocol)
                }
            }
        }
    }

    fun bootstrap(port: Int, protocol: String) {
        val server = ServerBootstrap()
        val parentGroup = NioEventLoopGroup()
        val childGroup = NioEventLoopGroup()
        server.group(parentGroup, childGroup)
        server.channel(NioServerSocketChannel::class.java)
        server.childHandler(HttpServerInitializer());
        val future = server.bind(port).sync()

        log.info("started $protocol on $port")

        future.channel().closeFuture().sync()
    }

}

fun main(args: Array<String>) {
    Bootstrap().start()
}