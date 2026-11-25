package org.apache.commons.beanutils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/* JADX INFO: loaded from: classes12.dex */
public class ConstructorUtils {
    private static final Class<?>[] EMPTY_CLASS_PARAMETERS = new Class[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    public static <T> T invokeConstructor(Class<T> cls, Object obj) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        return (T) invokeConstructor((Class) cls, toArray(obj));
    }

    public static <T> T invokeConstructor(Class<T> cls, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return (T) invokeConstructor(cls, objArr, clsArr);
    }

    public static <T> T invokeConstructor(Class<T> cls, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (clsArr == null) {
            clsArr = EMPTY_CLASS_PARAMETERS;
        }
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        Constructor matchingAccessibleConstructor = getMatchingAccessibleConstructor(cls, clsArr);
        if (matchingAccessibleConstructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + cls.getName());
        }
        return (T) matchingAccessibleConstructor.newInstance(objArr);
    }

    public static <T> T invokeExactConstructor(Class<T> cls, Object obj) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        return (T) invokeExactConstructor((Class) cls, toArray(obj));
    }

    public static <T> T invokeExactConstructor(Class<T> cls, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return (T) invokeExactConstructor(cls, objArr, clsArr);
    }

    public static <T> T invokeExactConstructor(Class<T> cls, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        if (clsArr == null) {
            clsArr = EMPTY_CLASS_PARAMETERS;
        }
        Constructor accessibleConstructor = getAccessibleConstructor(cls, clsArr);
        if (accessibleConstructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + cls.getName());
        }
        return (T) accessibleConstructor.newInstance(objArr);
    }

    public static <T> Constructor<T> getAccessibleConstructor(Class<T> cls, Class<?> cls2) {
        return getAccessibleConstructor(cls, (Class<?>[]) new Class[]{cls2});
    }

    public static <T> Constructor<T> getAccessibleConstructor(Class<T> cls, Class<?>[] clsArr) {
        try {
            return getAccessibleConstructor(cls.getConstructor(clsArr));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static <T> Constructor<T> getAccessibleConstructor(Constructor<T> constructor) {
        if (constructor != null && Modifier.isPublic(constructor.getModifiers()) && Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) {
            return constructor;
        }
        return null;
    }

    private static Object[] toArray(Object obj) {
        if (obj != null) {
            return new Object[]{obj};
        }
        return null;
    }

    private static <T> Constructor<T> getMatchingAccessibleConstructor(Class<T> cls, Class<?>[] clsArr) {
        try {
            Constructor<T> constructor = cls.getConstructor(clsArr);
            try {
                constructor.setAccessible(true);
            } catch (SecurityException unused) {
            }
            return constructor;
        } catch (NoSuchMethodException unused2) {
            int length = clsArr.length;
            for (Constructor<?> constructor2 : cls.getConstructors()) {
                Class<?>[] parameterTypes = constructor2.getParameterTypes();
                int length2 = parameterTypes.length;
                if (length2 == length) {
                    int i = 0;
                    while (true) {
                        if (i < length2) {
                            if (!MethodUtils.isAssignmentCompatible(parameterTypes[i], clsArr[i])) {
                                break;
                            }
                            i++;
                        } else {
                            Constructor<T> accessibleConstructor = getAccessibleConstructor(constructor2);
                            if (accessibleConstructor == null) {
                                break;
                            }
                            try {
                                accessibleConstructor.setAccessible(true);
                            } catch (SecurityException unused3) {
                            }
                            return accessibleConstructor;
                        }
                    }
                }
            }
            return null;
        }
    }
}
