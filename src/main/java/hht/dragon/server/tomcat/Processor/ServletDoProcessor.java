package hht.dragon.server.tomcat.Processor;

import hht.dragon.server.tomcat.constants.Constants;
import hht.dragon.server.tomcat.connector.Request;
import hht.dragon.server.tomcat.connector.RequestFacade;
import hht.dragon.server.tomcat.connector.Response;
import hht.dragon.server.tomcat.connector.ResponseFacade;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * servlet处理.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class ServletDoProcessor implements HttpDoProcessor {
    @Override
    public void process(Request request, Response response) {
        String uri = request.getRequestURI();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        try {
            // 创建类加载器
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);
            // class的存放目录或JAR文件的路径(此处为目录)
            String repository = (new URL("file", null,
                    classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Servlet servlet = null;

        try {
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet = (Servlet) myClass.newInstance();
            servlet.service(requestFacade, responseFacade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
