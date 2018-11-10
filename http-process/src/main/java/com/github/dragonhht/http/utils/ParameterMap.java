package com.github.dragonhht.http.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于存放请求的参数信息.
 *
 * @author: huang
 * Date: 2018/4/23
 */
public final class ParameterMap extends HashMap {

    private boolean locked = false;

    public ParameterMap() {
        super();
    }

    public ParameterMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ParameterMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ParameterMap(Map map) {
        super(map);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void clear() {
        if (locked) throw new IllegalStateException();
        super.clear();
    }

    public Object put(Object key, Object value) {
        if (locked) throw new IllegalStateException();
        return super.put(key, value);
    }

    public void putAll(Map map) {
        if (locked) throw new IllegalStateException();
        super.putAll(map);
    }

    public Object remove(Object key) {
        if (locked) throw new IllegalStateException();
        return super.remove(key);
    }
}
