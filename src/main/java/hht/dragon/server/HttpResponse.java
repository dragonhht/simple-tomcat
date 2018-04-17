package hht.dragon.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * HttpResponse.
 *
 * @author: huang
 * Date: 18-1-17
 */
public class HttpResponse {

    public HttpResponse() {

    }

    public void respon(OutputStream out, String path) {
        StringBuffer outStr = new StringBuffer();
        outStr.append("HTTP/1.1 200 OK\n");
        outStr.append("Content-Type: text/html;charset=UTF-8\n");
        outStr.append("\r\n");
        try {
            out.write(outStr.toString().getBytes());
            getFile(path, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFile(String path, OutputStream out) throws IOException {
        if (path == null) return;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            path = "html/error/404.html";
            fis = new FileInputStream(path);
        }

        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = fis.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }
}
