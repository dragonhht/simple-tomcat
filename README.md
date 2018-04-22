# 简易Tomcat

## 可以加载并调用简单的servlet类

-   使用外观类屏蔽Request类及Response类中特有的方法，防止在Servlet中的service方法中调用

## 使用连接器

-   参照tomcat4解析HTTP请求行

    - 使用[HttpRequestLine](./src/main/java/hht/dragon/server/tomcat/connector/HttpRequestLine.java)保存请求行信息
    
    - 使用[SocketInputStream](./src/main/java/hht/dragon/server/tomcat/connector/SocketInputStream.java)辅助获取HTTP请求信息
    
    - 在[HttpProcesser](./src/main/java/hht/dragon/server/tomcat/connector/HttpProcesser.java)中使用方法`parseRequest`解析请求行
    
-   参照tocmat4解析请求信息

    - 使用[HttpHeader](./src/main/java/hht/dragon/server/tomcat/connector/HttpHeader.java)保存请求头信息
    
    - 在[HttpProcesser](./src/main/java/hht/dragon/server/tomcat/connector/HttpProcesser.java)中使用方法`parseHeaders`解析请求头信息