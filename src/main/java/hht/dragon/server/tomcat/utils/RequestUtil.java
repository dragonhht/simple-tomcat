package hht.dragon.server.tomcat.utils;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * 帮助填充Request的工具类.
 *
 * @author: huang
 * Date: 2018/4/22
 */
public class RequestUtil {

    /**
     * 解析并获取cookie。
     * @param header http中与cookie相关的头信息的值
     * @return cookie数组
     */
    public static Cookie[] parseCookieHeader(String header) {
        if (header == null || header.length() < 1)
            return new Cookie[0];

        ArrayList<Cookie> cookies = new ArrayList<>();
        while (header.length() > 0) {
            int index = header.indexOf(";");
            if (index < 0) index = header.length();
            if (index == 0) break;

            String token = header.substring(0, index);
            if (index < header.length()) {
                header = header.substring(index + 1);
            } else {
                header = "";
            }

            try {
                int equls = token.indexOf("=");
                if (equls > 0) {
                    String name = token.substring(0, equls).trim();
                    String value = token.substring(equls + 1).trim();
                    cookies.add(new Cookie(name, value));
                }
            } catch (Throwable e) {

            }
        }
        return cookies.toArray(new Cookie[cookies.size()]);
    }

    /**
     * 解析uri中的参数.
     * @param result 用于存放获取到的参数
     * @param queryString uri中的参数信息
     * @param encoding 编码
     */
    public static void parseParameters(ParameterMap result, String queryString, String encoding)
                        throws UnsupportedEncodingException {
        queryString = URLDecoder.decode(queryString, encoding);
        String[] kvs = queryString.split("&");
        for (String kv : kvs) {
            String[] ss = kv.split("=");
            result.put(ss[0], ss[1]);
        }
    }

    /**
     * 解析post请求的参数.
     * @param result 用于存放获取到的参数
     * @param buf 含有参信息的byte数组
     * @param encoding 编码
     */
    public static void parseParameters(ParameterMap result, byte[] buf, String encoding) throws UnsupportedEncodingException {
        String queryString = new String(buf);
        parseParameters(result, queryString, encoding);
    }

}
