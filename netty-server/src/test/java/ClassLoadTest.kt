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
        val className = "com.github.dragonhht.framework.handler.DispatcherServlet"
        val classPath = "C:\\Users\\huang\\.m2\\repository\\com\\github\\dragonhht\\simple-web-framework\\1.0-SNAPSHOT"
        val classLoader = MyAppClassLoader(classPath)
        classLoader.loadClassByClassPath()
        println(Class.forName(className, true, classLoader).name)
    }

    @Test
    fun testClassLoadClass() {
        val className = "com.github.dragonhht.test.servlet.TestServlet"
        val classPath = "D:\\my_work_spance\\idea_workspance\\simple-tomcat\\WebRoot\\app"
        val clazz = MyAppClassLoader(classPath).findClass(className)
        println(clazz!!.name)
    }
}