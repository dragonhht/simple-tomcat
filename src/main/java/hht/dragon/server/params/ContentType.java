package hht.dragon.server.params;

import java.util.HashMap;
import java.util.Map;

/**
 * ContentType.
 *
 * @author: huang
 * Date: 18-2-1
 */
public enum ContentType {
    FORM("form-data"),
    XW("application/x-www-form-urlencoded"),
    BINARY("text/plain");

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(String s) {
        return value.equalsIgnoreCase(s);
    }
}
