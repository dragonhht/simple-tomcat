package hht.dragon.server.tomcat.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 连接器.
 * 负责创建ServerSocket，并等待Http请求
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class HttpConnector implements Runnable {

    private boolean isStoped;
    /** 请求协议. */
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }



    @Override
    public void run() {
        ServerSocket server = null;
        int port = 8080;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!isStoped) {
            Socket client = null;

            try {
                client = server.accept();
            } catch (IOException e) {
               continue;
            }
            HttpProcesser processer = new HttpProcesser();
            processer.process(client);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
