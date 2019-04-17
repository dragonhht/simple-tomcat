import com.github.dragonhht.model.Server
import com.github.dragonhht.utils.ReflectASMUtil
import com.github.dragonhht.utils.XmlUtil
import org.junit.Test

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
        XmlUtil.INSTANCE.parse(filePath, Server::class)
    }

    @Test
    fun testReflect() {
        val obj = Server()
        val clzz = obj::class.java
        clzz.declaredFields.forEach { println(it.name) }
        ReflectASMUtil.INSTANCE.setField("shutdown", obj, "dragonhht")
        println(obj)
    }

}