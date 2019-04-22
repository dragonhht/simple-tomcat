import com.github.dragonhht.classloader.MyAppClassLoader
import com.github.dragonhht.classloader.MyURLClassLoader
import org.junit.Test
import java.io.File
import java.net.URLClassLoader

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

    @Test
    fun testLoad() {
        val className = "com.github.dragonhht.framework.handler.DispatcherServlet"
        val classPath = "C:\\Users\\huang\\.m2\\repository\\com\\github\\dragonhht\\simple-web-framework\\"
        val urlClassLoader = URLClassLoader(arrayOf(File(classPath).toURI().toURL()))
        val clazz = urlClassLoader.loadClass(className)
        println(clazz.name)
    }

    @Test
    fun testLoadUrl() {
        val className = "javax.enterprise.inject.Produces"
        val classPath = "D:\\application\\apache-maven-3.6.0\\lib"
        val classLoader = MyURLClassLoader(classPath)
        classLoader.loadClassByPath()
        println(Class.forName(className, true, classLoader).name)
    }
}