package com.artemis;

import com.artemis.utils.reflect.ClassReflection;

/* JADX INFO: loaded from: classes8.dex */
public class InvalidComponentException extends RuntimeException {
    private Class<?> componentClass;

    public InvalidComponentException(Class<?> cls, String str) {
        super(message(cls, str));
        this.componentClass = cls;
    }

    public InvalidComponentException(Class<?> cls, String str, Exception exc) {
        super(message(cls, str), exc);
        this.componentClass = cls;
    }

    private static String message(Class<?> cls, String str) {
        return ClassReflection.getSimpleName(cls) + ": " + str;
    }

    public Class<?> getComponentClass() {
        return this.componentClass;
    }
}
