package hht.dragon.server.params;

/**
 * Http请求类型.
 *
 * @author: huang
 * Date: 18-1-18
 */
public enum HttpRequestType {

    GET("GET"), POST("POST"), OPTIONS("OPTIONS"), HEAD("HEAD"),
    PUT("PUT"), DELETE("DELETE"), TRACE("TRACE"), CONNECT("CONNECT");

    private String value;

    private HttpRequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(String s) {
        return value.equalsIgnoreCase(s);
    }

}
