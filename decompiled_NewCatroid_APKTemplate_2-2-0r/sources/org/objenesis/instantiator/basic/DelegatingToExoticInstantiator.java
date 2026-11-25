package org.objenesis.instantiator.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;

/* JADX INFO: loaded from: classes6.dex */
public abstract class DelegatingToExoticInstantiator<T> implements ObjectInstantiator<T> {
    private final ObjectInstantiator<T> wrapped;

    protected DelegatingToExoticInstantiator(String str, Class<T> cls) {
        this.wrapped = instantiator(str, cls, instantiatorConstructor(str, instantiatorClass(str)));
    }

    private ObjectInstantiator<T> instantiator(String str, Class<T> cls, Constructor<ObjectInstantiator<T>> constructor) {
        try {
            return constructor.newInstance(cls);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call constructor of " + str, e);
        }
    }

    private Class<ObjectInstantiator<T>> instantiatorClass(String str) {
        try {
            return (Class<ObjectInstantiator<T>>) Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new ObjenesisException(getClass().getSimpleName() + " now requires objenesis-exotic to be in the classpath", e);
        }
    }

    private Constructor<ObjectInstantiator<T>> instantiatorConstructor(String str, Class<ObjectInstantiator<T>> cls) {
        try {
            return cls.getConstructor(Class.class);
        } catch (NoSuchMethodException e) {
            throw new ObjenesisException("Try to find constructor taking a Class<T> in parameter on " + str + " but can't find it", e);
        }
    }

    @Override // org.objenesis.instantiator.ObjectInstantiator
    public T newInstance() {
        return this.wrapped.newInstance();
    }
}
