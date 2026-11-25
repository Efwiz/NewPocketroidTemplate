package org.objenesis.instantiator.perc;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.instantiator.annotations.Instantiator;
import org.objenesis.instantiator.annotations.Typology;

/* JADX INFO: loaded from: classes6.dex */
@Instantiator(Typology.SERIALIZATION)
public class PercSerializationInstantiator<T> implements ObjectInstantiator<T> {
    private final Method newInstanceMethod;
    private final Object[] typeArgs;

    public PercSerializationInstantiator(Class<T> cls) {
        Class<T> superclass = cls;
        while (Serializable.class.isAssignableFrom(superclass)) {
            superclass = superclass.getSuperclass();
        }
        try {
            Method declaredMethod = ObjectInputStream.class.getDeclaredMethod("noArgConstruct", Class.class, Object.class, Class.forName("COM.newmonics.PercClassLoader.Method"));
            this.newInstanceMethod = declaredMethod;
            declaredMethod.setAccessible(true);
            Object objInvoke = Class.forName("COM.newmonics.PercClassLoader.PercClass").getDeclaredMethod("getPercClass", Class.class).invoke(null, superclass);
            this.typeArgs = new Object[]{superclass, cls, objInvoke.getClass().getDeclaredMethod("findMethod", String.class).invoke(objInvoke, "<init>()V")};
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ObjenesisException(e);
        }
    }

    @Override // org.objenesis.instantiator.ObjectInstantiator
    public T newInstance() {
        try {
            return (T) this.newInstanceMethod.invoke(null, this.typeArgs);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ObjenesisException(e);
        }
    }
}
