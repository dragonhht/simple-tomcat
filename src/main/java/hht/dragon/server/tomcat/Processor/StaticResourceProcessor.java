package hht.dragon.server.tomcat.Processor;

import hht.dragon.server.tomcat.request.Request;
import hht.dragon.server.tomcat.response.Response;

import java.io.*;

/**
 * 静态文件处理.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class StaticResourceProcessor implements HttpProcessor {

    /** 静态资源文件存放路径. */
    private static final String WEB_ROOT;

    static {
        String webRoot = "WebRoot";
//        webRoot = webRoot.replaceAll("\\", "/");
        WEB_ROOT = webRoot;
    }


    @Override
    public void process(Request request, Response response) {
        String uri = request.getUri();
        if (uri == null) return;
        try {
            PrintWriter out = response.getWriter();
            senStaticResource(out, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void senStaticResource(PrintWriter out, String path) {
        StringBuffer outStr = new StringBuffer();
        outStr.append("HTTP/1.1 200 OK\n");
        outStr.append("Content-Type: text/html;charset=UTF-8\n");
        outStr.append("\r\n");
        getFile(path, outStr);
        out.write(outStr.toString());
        out.flush();
    }

    /**
     * 获取静态文件.
     * @param path 静态文件路径
     * @param outStr 接受内容的StringBuffer
     * @throws IOException
     */
    private void getFile(String path, StringBuffer outStr) {
        if (path == null) return;
        path = WEB_ROOT + path;
        FileInputStream fis = null;
        Reader reader = null;
        try {
            try {
                fis = new FileInputStream(path);

            } catch (FileNotFoundException e) {
                path = WEB_ROOT + "/html/error/404.html";
                fis = new FileInputStream(path);
            }
            reader = new InputStreamReader(fis);

            int len = 0;
            char[] buffer = new char[1024];
            while ((len = reader.read(buffer)) != -1) {
                outStr.append(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
