package com.artemis;

import com.artemis.PooledComponent;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;

/* JADX INFO: loaded from: classes8.dex */
public class ComponentPool<T extends PooledComponent> {
    private final Bag<T> cache;
    private Class<T> type;

    ComponentPool(Class<T> cls) {
        this.type = cls;
        this.cache = new Bag<>(cls);
    }

    <T extends PooledComponent> T obtain() {
        try {
            if (this.cache.size() > 0) {
                return this.cache.removeLast();
            }
            return (T) ClassReflection.newInstance(this.type);
        } catch (ReflectionException e) {
            throw new InvalidComponentException(this.type, e.getMessage(), e);
        }
    }

    void free(T t) {
        t.reset();
        this.cache.add(t);
    }
}
