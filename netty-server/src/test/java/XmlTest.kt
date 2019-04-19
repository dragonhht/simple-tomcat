import com.github.dragonhht.model.Server
import com.github.dragonhht.utils.ReflectionUtil
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import org.junit.Test
import java.io.File

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class XmlTest{

    @Test
    fun testParse() {
        val filePath = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\netty-server\\src\\main\\resources\\conf\\server.xml"
        //println(XmlUtil.INSTANCE.parse(filePath, Bootstrap::class.java))

        val xstream = XStream(DomDriver());//创建Xstram对象
        xstream.autodetectAnnotations(true)
        xstream.processAnnotations(Server::class.java)
        val server = xstream.fromXML(File(filePath))
        println(server)
    }

    @Test
    fun testReflect() {
        val obj = ReflectionUtil.INSTANCE.newInstance(Server::class.java)
        println(obj)
    }

}