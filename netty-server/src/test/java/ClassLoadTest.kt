import com.github.dragonhht.classloader.MyAppClassLoader
import org.junit.Test

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-19
 */
class ClassLoadTest {
    @Test
    fun testClassLoadJar() {
        val className = "net.minidev.asm.BasicFiledFilter"
        val classPath = "D:/application/apache-jmeter-5.0/lib"
        MyAppClassLoader(classPath).loadClassByClassPath()
    }

    @Test
    fun testClassLoadClass() {
        val className = "com.github.dragonhht.test.servlet.TestServlet"
        val classPath = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\WebRoot\\app"
        val clazz = MyAppClassLoader(classPath).findClass(className)
        println(clazz.name)
    }
}