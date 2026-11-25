package com.badlogic.gdx.utils.reflect;

import java.lang.reflect.InvocationTargetException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/reflect/Constructor.class
  classes8.dex
 */
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
