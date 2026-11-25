package com.google.firebase.encoders;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public final class FieldDescriptor {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f182name;
    private final Map<Class<?>, Object> properties;

    private FieldDescriptor(String str, Map<Class<?>, Object> map) {
        this.f182name = str;
        this.properties = map;
    }

    public String getName() {
        return this.f182name;
    }

    public <T extends Annotation> T getProperty(Class<T> cls) {
        return (T) this.properties.get(cls);
    }

    public static FieldDescriptor of(String str) {
        return new FieldDescriptor(str, Collections.emptyMap());
    }

    public static Builder builder(String str) {
        return new Builder(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FieldDescriptor)) {
            return false;
        }
        FieldDescriptor fieldDescriptor = (FieldDescriptor) obj;
        return this.f182name.equals(fieldDescriptor.f182name) && this.properties.equals(fieldDescriptor.properties);
    }

    public int hashCode() {
        return (this.f182name.hashCode() * 31) + this.properties.hashCode();
    }

    public String toString() {
        return "FieldDescriptor{name=" + this.f182name + ", properties=" + this.properties.values() + "}";
    }

    public static final class Builder {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f183name;
        private Map<Class<?>, Object> properties = null;

        Builder(String str) {
            this.f183name = str;
        }

        public <T extends Annotation> Builder withProperty(T t) {
            if (this.properties == null) {
                this.properties = new HashMap();
            }
            this.properties.put(t.annotationType(), t);
            return this;
        }

        public FieldDescriptor build() {
            Map mapUnmodifiableMap;
            String str = this.f183name;
            if (this.properties == null) {
                mapUnmodifiableMap = Collections.emptyMap();
            } else {
                mapUnmodifiableMap = Collections.unmodifiableMap(new HashMap(this.properties));
            }
            return new FieldDescriptor(str, mapUnmodifiableMap);
        }
    }
}
