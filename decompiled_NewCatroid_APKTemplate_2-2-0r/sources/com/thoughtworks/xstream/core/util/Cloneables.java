package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Cloneables.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Cloneables {
    public static Object clone(Object obj) {
        if (!(obj instanceof Cloneable)) {
            return null;
        }
        if (obj.getClass().isArray()) {
            Class<?> componentType = obj.getClass().getComponentType();
            if (!componentType.isPrimitive()) {
                return ((Object[]) obj).clone();
            }
            int length = Array.getLength(obj);
            Object objNewInstance = Array.newInstance(componentType, length);
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    return objNewInstance;
                }
                Array.set(objNewInstance, i, Array.get(obj, i));
                length = i;
            }
        } else {
            try {
                return obj.getClass().getMethod("clone", null).invoke(obj, null);
            } catch (IllegalAccessException e) {
                throw new ObjectAccessException("Cannot clone Cloneable type", e);
            } catch (NoSuchMethodException e2) {
                throw new ObjectAccessException("Cloneable type has no clone method", e2);
            } catch (InvocationTargetException e3) {
                throw new ObjectAccessException("Exception cloning Cloneable type", e3.getCause());
            }
        }
    }

    public static Object cloneIfPossible(Object obj) {
        Object objClone = clone(obj);
        return objClone == null ? obj : objClone;
    }
}
