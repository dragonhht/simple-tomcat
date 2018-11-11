package com.github.dragonhht;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟并发.
 *
 * @author: huang
 * @Date: 18-11-11
 */
public class ServerTest {

    public void send() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/html/World.html")
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "4ff8ba97-53e0-86f8-b99c-4baef9468a32")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());
    }

    public void test() {
        int threadCount = 9000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Runnable thread = () -> {
                latch.countDown();
                try {
                    latch.await();
                    send();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            };
            new Thread(thread).start();
        }
    }

    public static void main(String[] args) {
        ServerTest serverTest = new ServerTest();
        serverTest.test();
    }

}
