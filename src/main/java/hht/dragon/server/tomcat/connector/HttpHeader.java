package hht.dragon.server.tomcat.connector;

/**
 * 用于保存请求头信息.
 *
 * @author: huang
 * Date: 2018/4/22
 */
final class HttpHeader {

    static final int INITIAL_NAME_SIZE = 32;
    static final int INITIAL_VALUE_SIZE = 64;
    static final int MAX_NAME_SIZE = 128;
    static final int MAX_VALUE_SIZE = 4096;

    char[] name;
    int nameEnd;
    char[] value;
    int valueEnd;
    int hashCode = 0;

    public HttpHeader() {
        this(new char[INITIAL_NAME_SIZE], 0, new char[INITIAL_VALUE_SIZE], 0);
    }

    public HttpHeader(String name, String value) {
        this.name = name.toLowerCase().toCharArray();
        this.nameEnd = name.length();
        this.value = value.toCharArray();
        this.valueEnd = value.length();
    }

    HttpHeader(char[] name, int nameEnd, char[] value, int valueEnd) {
        this.name = name;
        this.nameEnd = nameEnd;
        this.value = value;
        this.valueEnd = valueEnd;
    }

    public void recycle() {
        nameEnd = 0;
        valueEnd = 0;
        hashCode = 0;
    }

    /**
     * 判断name是否与指定的数组相同(都为小写).
     * @param buf
     * @param end
     * @return
     */
    public boolean equals(char[] buf, int end) {
        if (end != nameEnd) return false;
        for (int i = 0; i < end; i++) {
            if (buf[i] != name[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(char[] buf) {
        return equals(buf, buf.length);
    }

    public boolean equals(String str) {
        return equals(str.toLowerCase().toCharArray(), str.length());
    }

    /**
     * 判断value是否与指定的数组相同.
     * @param buf
     * @param end
     * @return
     */
    boolean valueEquals(char[] buf, int end) {
        if (end != valueEnd) return false;
        for (int i = 0; i < end; i++) {
            if (buf[i] != value[i])
                return false;
        }
        return true;
    }

    boolean valueEquals(char[] buf) {
        return valueEquals(buf, buf.length);
    }

    boolean valueEquals(String str) {
        return valueEquals(str.toCharArray(), str.length());
    }

    int valueIndexOf(char c, int start) {
        for (int i = start; i < valueEnd; i++) {
            if (value[i] == c) return i;
        }
        return -1;
    }

    /**
     * 判断value中是否包含给定的字符.
     * @param buf
     * @param end
     * @return
     */
    boolean valueIncludes(char[] buf, int end) {
        char firstValue = buf[0];
        int pos = 0;
        while (pos < valueEnd) {
            pos = valueIndexOf(firstValue, 0);
            if (pos == -1) return false;
            if ((valueEnd - pos) < end) return false;
            for (int i = 0; i < end; i++) {
                if (value[i + pos] != buf[i])
                    break;
                if (i == end - 1)
                    return true;
            }
            pos++;
        }
        return false;
    }

    boolean valueIncludes(char[] buf) {
        return valueIncludes(buf, buf.length);
    }

    boolean valueIncludes(String str) {
        return valueIncludes(str.toCharArray(), str.length());
    }

    public boolean equals(HttpHeader header) {
        return equals(header.name, header.nameEnd);
    }

    boolean headerEquals(HttpHeader header) {
        return equals(header.name, header.nameEnd)
                && valueEquals(header.value, header.valueEnd);
    }

    public int hashCode() {
        int h = hashCode;
        if (h == 0) {
            int off = 0;
            char val[] = name;
            int len = nameEnd;
            for (int i = 0; i < len; i++)
                h = 31*h + val[off++];
            hashCode = h;
        }
        return h;
    }

    public boolean equals(Object obj) {
        if (obj instanceof String)
            return equals(((String) obj).toLowerCase());
        if (obj instanceof HttpHeader)
            return equals((HttpHeader) obj);
        return false;
    }

    public String getName() {
        return new String(name, 0, nameEnd);
    }

    public String getValue() {
        return new String(value, 0, valueEnd);
    }

}
