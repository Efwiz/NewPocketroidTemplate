package org.objenesis.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;

/* JADX INFO: loaded from: classes6.dex */
public class SingleInstantiatorStrategy implements InstantiatorStrategy {
    private final Constructor<?> constructor;

    public <T extends ObjectInstantiator<?>> SingleInstantiatorStrategy(Class<T> cls) {
        try {
            this.constructor = cls.getConstructor(Class.class);
        } catch (NoSuchMethodException e) {
            throw new ObjenesisException(e);
        }
    }

    @Override // org.objenesis.strategy.InstantiatorStrategy
    public <T> ObjectInstantiator<T> newInstantiatorOf(Class<T> cls) {
        try {
            return (ObjectInstantiator) this.constructor.newInstance(cls);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ObjenesisException(e);
        }
    }
}
