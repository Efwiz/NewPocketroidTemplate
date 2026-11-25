package org.apache.commons.beanutils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* JADX INFO: loaded from: classes12.dex */
public class MethodUtils {
    private static boolean CACHE_METHODS = true;
    private static final Class<?>[] EMPTY_CLASS_PARAMETERS = new Class[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final Map<MethodDescriptor, Reference<Method>> cache = Collections.synchronizedMap(new WeakHashMap());
    private static boolean loggedAccessibleWarning = false;

    public static synchronized void setCacheMethods(boolean z) {
        CACHE_METHODS = z;
        if (!z) {
            clearCache();
        }
    }

    public static synchronized int clearCache() {
        int size;
        Map<MethodDescriptor, Reference<Method>> map = cache;
        size = map.size();
        map.clear();
        return size;
    }

    public static Object invokeMethod(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeMethod(obj, str, toArray(obj2));
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeMethod(obj, str, objArr, clsArr);
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (clsArr == null) {
            clsArr = EMPTY_CLASS_PARAMETERS;
        }
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        Method matchingAccessibleMethod = getMatchingAccessibleMethod(obj.getClass(), str, clsArr);
        if (matchingAccessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on object: " + obj.getClass().getName());
        }
        return matchingAccessibleMethod.invoke(obj, objArr);
    }

    public static Object invokeExactMethod(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeExactMethod(obj, str, toArray(obj2));
    }

    public static Object invokeExactMethod(Object obj, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeExactMethod(obj, str, objArr, clsArr);
    }

    public static Object invokeExactMethod(Object obj, String str, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        if (clsArr == null) {
            clsArr = EMPTY_CLASS_PARAMETERS;
        }
        Method accessibleMethod = getAccessibleMethod(obj.getClass(), str, clsArr);
        if (accessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on object: " + obj.getClass().getName());
        }
        return accessibleMethod.invoke(obj, objArr);
    }

    public static Object invokeExactStaticMethod(Class<?> cls, String str, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        if (clsArr == null) {
            clsArr = EMPTY_CLASS_PARAMETERS;
        }
        Method accessibleMethod = getAccessibleMethod(cls, str, clsArr);
        if (accessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on class: " + cls.getName());
        }
        return accessibleMethod.invoke(null, objArr);
    }

    public static Object invokeStaticMethod(Class<?> cls, String str, Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeStaticMethod(cls, str, toArray(obj));
    }

    public static Object invokeStaticMethod(Class<?> cls, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeStaticMethod(cls, str, objArr, clsArr);
    }

    public static Object invokeStaticMethod(Class<?> cls, String str, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (clsArr == null) {
            clsArr = EMPTY_CLASS_PARAMETERS;
        }
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        Method matchingAccessibleMethod = getMatchingAccessibleMethod(cls, str, clsArr);
        if (matchingAccessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on class: " + cls.getName());
        }
        return matchingAccessibleMethod.invoke(null, objArr);
    }

    public static Object invokeExactStaticMethod(Class<?> cls, String str, Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeExactStaticMethod(cls, str, toArray(obj));
    }

    public static Object invokeExactStaticMethod(Class<?> cls, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        return invokeExactStaticMethod(cls, str, objArr, clsArr);
    }

    private static Object[] toArray(Object obj) {
        if (obj != null) {
            return new Object[]{obj};
        }
        return null;
    }

    public static Method getAccessibleMethod(Class<?> cls, String str, Class<?> cls2) {
        return getAccessibleMethod(cls, str, (Class<?>[]) new Class[]{cls2});
    }

    public static Method getAccessibleMethod(Class<?> cls, String str, Class<?>[] clsArr) {
        try {
            MethodDescriptor methodDescriptor = new MethodDescriptor(cls, str, clsArr, true);
            Method cachedMethod = getCachedMethod(methodDescriptor);
            if (cachedMethod != null) {
                return cachedMethod;
            }
            Method accessibleMethod = getAccessibleMethod(cls, cls.getMethod(str, clsArr));
            cacheMethod(methodDescriptor, accessibleMethod);
            return accessibleMethod;
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Method getAccessibleMethod(Method method) {
        if (method == null) {
            return null;
        }
        return getAccessibleMethod(method.getDeclaringClass(), method);
    }

    public static Method getAccessibleMethod(Class<?> cls, Method method) {
        boolean zEquals;
        if (method == null || !Modifier.isPublic(method.getModifiers())) {
            return null;
        }
        if (cls == null) {
            cls = method.getDeclaringClass();
            zEquals = true;
        } else {
            zEquals = cls.equals(method.getDeclaringClass());
            if (!method.getDeclaringClass().isAssignableFrom(cls)) {
                throw new IllegalArgumentException(cls.getName() + " is not assignable from " + method.getDeclaringClass().getName());
            }
        }
        if (Modifier.isPublic(cls.getModifiers())) {
            if (!zEquals && !Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
                setMethodAccessible(method);
            }
            return method;
        }
        String name2 = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method accessibleMethodFromInterfaceNest = getAccessibleMethodFromInterfaceNest(cls, name2, parameterTypes);
        return accessibleMethodFromInterfaceNest == null ? getAccessibleMethodFromSuperclass(cls, name2, parameterTypes) : accessibleMethodFromInterfaceNest;
    }

    private static Method getAccessibleMethodFromSuperclass(Class<?> cls, String str, Class<?>[] clsArr) {
        for (Class<? super Object> superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            if (Modifier.isPublic(superclass.getModifiers())) {
                try {
                    return superclass.getMethod(str, clsArr);
                } catch (NoSuchMethodException unused) {
                    return null;
                }
            }
        }
        return null;
    }

    private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls, String str, Class<?>[] clsArr) {
        Method accessibleMethodFromInterfaceNest = null;
        while (cls != null) {
            Class<?>[] interfaces = cls.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (Modifier.isPublic(interfaces[i].getModifiers())) {
                    try {
                        accessibleMethodFromInterfaceNest = interfaces[i].getDeclaredMethod(str, clsArr);
                    } catch (NoSuchMethodException unused) {
                    }
                    if (accessibleMethodFromInterfaceNest != null) {
                        return accessibleMethodFromInterfaceNest;
                    }
                    accessibleMethodFromInterfaceNest = getAccessibleMethodFromInterfaceNest(interfaces[i], str, clsArr);
                    if (accessibleMethodFromInterfaceNest != null) {
                        return accessibleMethodFromInterfaceNest;
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    public static Method getMatchingAccessibleMethod(Class<?> cls, String str, Class<?>[] clsArr) {
        String str2 = str;
        Log log = LogFactory.getLog(MethodUtils.class);
        if (log.isTraceEnabled()) {
            log.trace("Matching name=" + str2 + " on " + cls);
        }
        int i = 0;
        MethodDescriptor methodDescriptor = new MethodDescriptor(cls, str2, clsArr, false);
        try {
            Method cachedMethod = getCachedMethod(methodDescriptor);
            if (cachedMethod != null) {
                return cachedMethod;
            }
            Method method = cls.getMethod(str, clsArr);
            if (log.isTraceEnabled()) {
                log.trace("Found straight match: " + method);
                log.trace("isPublic:" + Modifier.isPublic(method.getModifiers()));
            }
            setMethodAccessible(method);
            cacheMethod(methodDescriptor, method);
            return method;
        } catch (NoSuchMethodException unused) {
            int length = clsArr.length;
            Method[] methods = cls.getMethods();
            int length2 = methods.length;
            Method method2 = null;
            float f = Float.MAX_VALUE;
            int i2 = 0;
            while (i2 < length2) {
                Method method3 = methods[i2];
                if (method3.getName().equals(str2)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Found matching name:");
                        log.trace(method3);
                    }
                    Class<?>[] parameterTypes = method3.getParameterTypes();
                    int length3 = parameterTypes.length;
                    if (length3 == length) {
                        int i3 = i;
                        while (true) {
                            if (i3 < length3) {
                                if (log.isTraceEnabled()) {
                                    log.trace("Param=" + clsArr[i3].getName());
                                    log.trace("Method=" + parameterTypes[i3].getName());
                                }
                                if (!isAssignmentCompatible(parameterTypes[i3], clsArr[i3])) {
                                    if (!log.isTraceEnabled()) {
                                        break;
                                    }
                                    log.trace(parameterTypes[i3] + " is not assignable from " + clsArr[i3]);
                                    break;
                                }
                                i3++;
                            } else {
                                Method accessibleMethod = getAccessibleMethod(cls, method3);
                                if (accessibleMethod != null) {
                                    if (log.isTraceEnabled()) {
                                        log.trace(accessibleMethod + " accessible version of " + method3);
                                    }
                                    setMethodAccessible(accessibleMethod);
                                    float totalTransformationCost = getTotalTransformationCost(clsArr, accessibleMethod.getParameterTypes());
                                    if (totalTransformationCost < f) {
                                        method2 = accessibleMethod;
                                        f = totalTransformationCost;
                                    }
                                }
                                log.trace("Couldn't find accessible method.");
                                break;
                            }
                        }
                    }
                }
                i2++;
                str2 = str;
                i = 0;
            }
            if (method2 != null) {
                cacheMethod(methodDescriptor, method2);
            } else {
                log.trace("No match found.");
            }
            return method2;
        }
    }

    private static void setMethodAccessible(Method method) {
        try {
            if (method.isAccessible()) {
                return;
            }
            method.setAccessible(true);
        } catch (SecurityException e) {
            Log log = LogFactory.getLog(MethodUtils.class);
            if (!loggedAccessibleWarning) {
                try {
                    String property = System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION);
                    if (property.charAt(0) == '1' && (property.charAt(2) == '0' || property.charAt(2) == '1' || property.charAt(2) == '2' || property.charAt(2) == '3')) {
                        log.warn("Current Security Manager restricts use of workarounds for reflection bugs  in pre-1.4 JVMs.");
                    }
                } catch (SecurityException unused) {
                }
                loggedAccessibleWarning = true;
            }
            log.debug("Cannot setAccessible on method. Therefore cannot use jvm access bug workaround.", e);
        }
    }

    private static float getTotalTransformationCost(Class<?>[] clsArr, Class<?>[] clsArr2) {
        float objectTransformationCost = 0.0f;
        for (int i = 0; i < clsArr.length; i++) {
            objectTransformationCost += getObjectTransformationCost(clsArr[i], clsArr2[i]);
        }
        return objectTransformationCost;
    }

    private static float getObjectTransformationCost(Class<?> cls, Class<?> cls2) {
        Class<?> primitiveWrapper;
        float f = 0.0f;
        while (cls != null && !cls2.equals(cls)) {
            if ((cls2.isPrimitive() && (primitiveWrapper = getPrimitiveWrapper(cls2)) != null && primitiveWrapper.equals(cls)) || (cls2.isInterface() && isAssignmentCompatible(cls2, cls))) {
                f += 0.25f;
                break;
            }
            f += 1.0f;
            cls = cls.getSuperclass();
        }
        return cls == null ? f + 1.5f : f;
    }

    public static final boolean isAssignmentCompatible(Class<?> cls, Class<?> cls2) {
        Class<?> primitiveWrapper;
        if (cls.isAssignableFrom(cls2)) {
            return true;
        }
        if (!cls.isPrimitive() || (primitiveWrapper = getPrimitiveWrapper(cls)) == null) {
            return false;
        }
        return primitiveWrapper.equals(cls2);
    }

    public static Class<?> getPrimitiveWrapper(Class<?> cls) {
        if (Boolean.TYPE.equals(cls)) {
            return Boolean.class;
        }
        if (Float.TYPE.equals(cls)) {
            return Float.class;
        }
        if (Long.TYPE.equals(cls)) {
            return Long.class;
        }
        if (Integer.TYPE.equals(cls)) {
            return Integer.class;
        }
        if (Short.TYPE.equals(cls)) {
            return Short.class;
        }
        if (Byte.TYPE.equals(cls)) {
            return Byte.class;
        }
        if (Double.TYPE.equals(cls)) {
            return Double.class;
        }
        if (Character.TYPE.equals(cls)) {
            return Character.class;
        }
        return null;
    }

    public static Class<?> getPrimitiveType(Class<?> cls) {
        if (Boolean.class.equals(cls)) {
            return Boolean.TYPE;
        }
        if (Float.class.equals(cls)) {
            return Float.TYPE;
        }
        if (Long.class.equals(cls)) {
            return Long.TYPE;
        }
        if (Integer.class.equals(cls)) {
            return Integer.TYPE;
        }
        if (Short.class.equals(cls)) {
            return Short.TYPE;
        }
        if (Byte.class.equals(cls)) {
            return Byte.TYPE;
        }
        if (Double.class.equals(cls)) {
            return Double.TYPE;
        }
        if (Character.class.equals(cls)) {
            return Character.TYPE;
        }
        Log log = LogFactory.getLog(MethodUtils.class);
        if (!log.isDebugEnabled()) {
            return null;
        }
        log.debug("Not a known primitive wrapper class: " + cls);
        return null;
    }

    public static Class<?> toNonPrimitiveClass(Class<?> cls) {
        Class<?> primitiveWrapper;
        return (!cls.isPrimitive() || (primitiveWrapper = getPrimitiveWrapper(cls)) == null) ? cls : primitiveWrapper;
    }

    private static Method getCachedMethod(MethodDescriptor methodDescriptor) {
        Reference<Method> reference;
        if (!CACHE_METHODS || (reference = cache.get(methodDescriptor)) == null) {
            return null;
        }
        return reference.get();
    }

    private static void cacheMethod(MethodDescriptor methodDescriptor, Method method) {
        if (!CACHE_METHODS || method == null) {
            return;
        }
        cache.put(methodDescriptor, new WeakReference(method));
    }

    private static class MethodDescriptor {
        private final Class<?> cls;
        private final boolean exact;
        private final int hashCode;
        private final String methodName;
        private final Class<?>[] paramTypes;

        public MethodDescriptor(Class<?> cls, String str, Class<?>[] clsArr, boolean z) {
            if (cls == null) {
                throw new IllegalArgumentException("Class cannot be null");
            }
            if (str != null) {
                clsArr = clsArr == null ? MethodUtils.EMPTY_CLASS_PARAMETERS : clsArr;
                this.cls = cls;
                this.methodName = str;
                this.paramTypes = clsArr;
                this.exact = z;
                this.hashCode = str.length();
                return;
            }
            throw new IllegalArgumentException("Method Name cannot be null");
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MethodDescriptor)) {
                return false;
            }
            MethodDescriptor methodDescriptor = (MethodDescriptor) obj;
            return this.exact == methodDescriptor.exact && this.methodName.equals(methodDescriptor.methodName) && this.cls.equals(methodDescriptor.cls) && Arrays.equals(this.paramTypes, methodDescriptor.paramTypes);
        }

        public int hashCode() {
            return this.hashCode;
        }
    }
}
