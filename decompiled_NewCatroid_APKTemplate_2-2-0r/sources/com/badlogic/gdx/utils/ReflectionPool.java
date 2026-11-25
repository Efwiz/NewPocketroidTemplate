package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ReflectionPool.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ReflectionPool<T> extends Pool<T> {
    private final Constructor constructor;

    public ReflectionPool(Class<T> cls) {
        this(cls, 16, Integer.MAX_VALUE);
    }

    public ReflectionPool(Class<T> cls, int i) {
        this(cls, i, Integer.MAX_VALUE);
    }

    public ReflectionPool(Class<T> cls, int i, int i2) {
        super(i, i2);
        Constructor constructorFindConstructor = findConstructor(cls);
        this.constructor = constructorFindConstructor;
        if (constructorFindConstructor == null) {
            throw new RuntimeException("Class cannot be created (missing no-arg constructor): " + cls.getName());
        }
    }

    private Constructor findConstructor(Class<T> cls) {
        try {
            try {
                return ClassReflection.getConstructor(cls, null);
            } catch (ReflectionException unused) {
                return null;
            }
        } catch (Exception unused2) {
            Constructor declaredConstructor = ClassReflection.getDeclaredConstructor(cls, null);
            declaredConstructor.setAccessible(true);
            return declaredConstructor;
        }
    }

    @Override // com.badlogic.gdx.utils.Pool
    protected T newObject() {
        try {
            return (T) this.constructor.newInstance(null);
        } catch (Exception e) {
            throw new GdxRuntimeException("Unable to create new instance: " + this.constructor.getDeclaringClass().getName(), e);
        }
    }
}
