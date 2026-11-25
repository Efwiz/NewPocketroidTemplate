package com.badlogic.gdx.maps;

import com.badlogic.gdx.utils.ObjectMap;
import java.util.Iterator;
import java.util.Objects;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/MapProperties.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class MapProperties {
    private ObjectMap<String, Object> properties = new ObjectMap<>();

    public boolean containsKey(String str) {
        return this.properties.containsKey(str);
    }

    public Object get(String str) {
        return this.properties.get(str);
    }

    public <T> T get(String str, Class<T> cls) {
        return (T) get(str);
    }

    public <T> T get(String str, T t, Class<T> cls) {
        T t2 = (T) get(str);
        return t2 == null ? t : t2;
    }

    public void put(String str, Object obj) {
        this.properties.put(str, obj);
    }

    public void putAll(MapProperties mapProperties) {
        this.properties.putAll(mapProperties.properties);
    }

    public void remove(String str) {
        this.properties.remove(str);
    }

    public void clear() {
        this.properties.clear();
    }

    public Iterator<String> getKeys() {
        return this.properties.keys();
    }

    public Iterator<Object> getValues() {
        return this.properties.values();
    }

    public String toString() {
        return "MapProperties{properties=" + this.properties + '}';
    }

    public boolean equals(Object obj) {
        if (obj instanceof MapProperties) {
            return Objects.equals(this.properties, ((MapProperties) obj).properties);
        }
        return false;
    }

    public int hashCode() {
        return this.properties.hashCode();
    }
}
