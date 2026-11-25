package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.strategy.InstantiatorStrategy;

/* JADX INFO: loaded from: classes8.dex */
public class DefaultInstantiatorStrategy implements InstantiatorStrategy {
    private InstantiatorStrategy fallbackStrategy;

    public DefaultInstantiatorStrategy() {
    }

    public DefaultInstantiatorStrategy(InstantiatorStrategy instantiatorStrategy) {
        this.fallbackStrategy = instantiatorStrategy;
    }

    public void setFallbackInstantiatorStrategy(InstantiatorStrategy instantiatorStrategy) {
        this.fallbackStrategy = instantiatorStrategy;
    }

    public InstantiatorStrategy getFallbackInstantiatorStrategy() {
        return this.fallbackStrategy;
    }

    @Override // org.objenesis.strategy.InstantiatorStrategy
    public ObjectInstantiator newInstantiatorOf(final Class cls) {
        final Constructor declaredConstructor;
        if (!Util.isAndroid && (cls.getEnclosingClass() == null || !cls.isMemberClass() || Modifier.isStatic(cls.getModifiers()))) {
            try {
                final ConstructorAccess constructorAccess = ConstructorAccess.get(cls);
                return new ObjectInstantiator() { // from class: com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy.1
                    @Override // org.objenesis.instantiator.ObjectInstantiator
                    public Object newInstance() {
                        try {
                            return constructorAccess.newInstance();
                        } catch (Exception e) {
                            throw new KryoException("Error constructing instance of class: " + Util.className(cls), e);
                        }
                    }
                };
            } catch (Exception unused) {
            }
        }
        try {
            try {
                declaredConstructor = cls.getConstructor(null);
            } catch (Exception unused2) {
                declaredConstructor = cls.getDeclaredConstructor(null);
                declaredConstructor.setAccessible(true);
            }
            return new ObjectInstantiator() { // from class: com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy.2
                @Override // org.objenesis.instantiator.ObjectInstantiator
                public Object newInstance() {
                    try {
                        return declaredConstructor.newInstance(new Object[0]);
                    } catch (Exception e) {
                        throw new KryoException("Error constructing instance of class: " + Util.className(cls), e);
                    }
                }
            };
        } catch (Exception unused3) {
            InstantiatorStrategy instantiatorStrategy = this.fallbackStrategy;
            if (instantiatorStrategy == null) {
                if (cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers())) {
                    throw new KryoException("Class cannot be created (non-static member class): " + Util.className(cls));
                }
                StringBuilder sb = new StringBuilder("Class cannot be created (missing no-arg constructor): " + Util.className(cls));
                if (cls.getSimpleName().equals("")) {
                    sb.append("\nNote: This is an anonymous class, which is not serializable by default in Kryo. Possible solutions:\n1. Remove uses of anonymous classes, including double brace initialization, from the containing\nclass. This is the safest solution, as anonymous classes don't have predictable names for serialization.\n2. Register a FieldSerializer for the containing class and call FieldSerializer\nsetIgnoreSyntheticFields(false) on it. This is not safe but may be sufficient temporarily.");
                }
                throw new KryoException(sb.toString());
            }
            return instantiatorStrategy.newInstantiatorOf(cls);
        }
    }
}
