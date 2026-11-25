package com.artemis.utils.reflect;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes8.dex */
public final class Constructor {
    private final java.lang.reflect.Constructor constructor;

    Constructor(java.lang.reflect.Constructor constructor) {
        this.constructor = constructor;
    }

    public Class[] getParameterTypes() {
        return this.constructor.getParameterTypes();
    }

    public Class getDeclaringClass() {
        return this.constructor.getDeclaringClass();
    }

    public boolean isAccessible() {
        return this.constructor.isAccessible();
    }

    public void setAccessible(boolean z) {
        this.constructor.setAccessible(z);
    }

    public int getModifiers() {
        return this.constructor.getModifiers();
    }

    public Object newInstance(Object... objArr) throws ReflectionException {
        try {
            return this.constructor.newInstance(objArr);
        } catch (IllegalAccessException e) {
            throw new ReflectionException("Could not instantiate instance of class: " + getDeclaringClass().getName(), e);
        } catch (IllegalArgumentException e2) {
            throw new ReflectionException("Illegal argument(s) supplied to constructor for class: " + getDeclaringClass().getName(), e2);
        } catch (InstantiationException e3) {
            throw new ReflectionException("Could not instantiate instance of class: " + getDeclaringClass().getName(), e3);
        } catch (InvocationTargetException e4) {
            throw new ReflectionException("Exception occurred in constructor for class: " + getDeclaringClass().getName(), e4);
        }
    }
}
