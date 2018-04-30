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
    
-   解析请求参数(当需使用时解析，即当业务中未使用到参数，则不解析)

    -   解析的主要方法为[Request](./src/main/java/hht/dragon/server/tomcat/connector/Request.java)类中的`parseParameters`方法
    
    -   解析后的参数放入[ParameterMap](./src/main/java/hht/dragon/server/tomcat/utils/ParameterMap.java)的一个实例中，该类继承了`HashMap`，并在该类中使用成员变量`locked`控制参数是否可写入，防止用户非法添加参数
    
