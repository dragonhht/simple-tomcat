package hht.dragon.server.tomcat.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于处理错误信息的工具类.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class StringManager {

    private static Map<String, StringManager> managers = new ConcurrentHashMap<>();

    private StringManager() {

    }

    public static synchronized StringManager getInstance(String packageName) {
        StringManager manager = managers.get(packageName);
        if (manager == null) {
            manager = new StringManager();
            managers.put(packageName, manager);
        }
        return manager;
    }

}
