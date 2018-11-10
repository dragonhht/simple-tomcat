package com.github.dragonhht.boostrap.connector;

import com.github.dragonhht.boostrap.Processor.ServletDoProcessor;
import com.github.dragonhht.boostrap.Processor.StaticResourceDoProcessor;
import com.github.dragonhht.http.request.RequestProcesser;
import com.github.dragonhht.http.request.SocketInputStream;
import com.github.dragonhht.http.response.ResponseProcesser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 负责处理请求.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class HttpProcesser {

    public void process(Socket socket) {
        HttpServletRequest request;
        InputStream input;
        OutputStream output;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
            SocketInputStream socketInputStream = new SocketInputStream(input, 2048);

            request = RequestProcesser.getInstance().process(socketInputStream);

            HttpServletResponse response = ResponseProcesser.getInstance().process(output, request);

            if (request.getRequestURI().startsWith("/servlet/")) {
                ServletDoProcessor processor = new ServletDoProcessor();
                processor.process(request, response);
            } else {
                StaticResourceDoProcessor processor = new StaticResourceDoProcessor();
                processor.process(request, response);
            }

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
