package hht.dragon.server.utils;

/**
 * 字符串处理,工具类.
 *
 * @author: huang
 * Date: 18-4-17
 */

public class StringUtil {

    private static final StringUtil util = new StringUtil();

    private StringUtil() {}

    public static StringUtil getInstance() {
        return util;
    }

    /**
     * 将含‘-’的字符串转为驼峰格式。<br>
     * @param key 需要转换的字符串
     * @return 转换后的字符串
     */
    public String convertHeaderKey(String key) {
        String[] keys = key.split("-");
        StringBuilder str = new StringBuilder();
        for (String k : keys) {
            str.append(toUpperCaseFirst(k));
        }
        return new String(str);
    }

    /**
     * 将字符串的第一个字母转为大写.
     * @param str 字符串
     * @return 转换后的字符串
     */
    public String toUpperCaseFirst(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 97 && chars[0] <= 122) {
            chars[0] -= 32;
        }
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] >= 65 && chars[i] <= 90) {
                chars[i] += 32;
            }
        }
        return new String(chars);
    }

}
