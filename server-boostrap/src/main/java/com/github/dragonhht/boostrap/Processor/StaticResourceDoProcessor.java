package com.github.dragonhht.boostrap.Processor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 静态文件处理.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class StaticResourceDoProcessor implements HttpDoProcessor {

    /** 静态资源文件存放路径. */
    private static final String WEB_ROOT = "WebRoot";

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        senStaticResource(uri, response);
    }

    /**
     * 发送静态资源.
     */
    private void senStaticResource(String path, HttpServletResponse response) {
        StringBuffer outStr = new StringBuffer();
        outStr.append("HTTP/1.1 200 OK\n");
        outStr.append("Content-Type: text/html;charset=UTF-8\n");
        outStr.append("\r\n");
        try {
            ServletOutputStream out = response.getOutputStream();
            out.write(outStr.toString().getBytes());
            getFile(path, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取静态文件.
     */
    private void getFile(String path, ServletOutputStream out) {
        if (path == null) return;
        path = WEB_ROOT + path;
        FileInputStream fis = null;
        try {
            try {
                fis = new FileInputStream(path);

            } catch (FileNotFoundException e) {
                path = WEB_ROOT + "/html/error/404.html";
                fis = new FileInputStream(path);
            }

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
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
        }
    }
}
