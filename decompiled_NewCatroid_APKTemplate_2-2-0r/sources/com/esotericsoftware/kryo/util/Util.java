package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.SerializerFactory;
import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.esotericsoftware.minlog.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.SystemProperties;
import org.objenesis.strategy.PlatformDescription;

/* JADX INFO: loaded from: classes8.dex */
public class Util {
    public static final boolean isAndroid = PlatformDescription.DALVIK.equals(System.getProperty(SystemProperties.JAVA_VM_NAME));
    public static final int maxArraySize = 2147483639;
    private static final Map<Class<?>, Class<?>> primitiveWrappers;
    public static final boolean unsafe;

    static {
        boolean z = false;
        if ("false".equals(System.getProperty("kryo.unsafe"))) {
            if (Log.TRACE) {
                Log.trace("kryo", "Unsafe is disabled.");
            }
        } else {
            try {
                if (Class.forName("com.esotericsoftware.kryo.unsafe.UnsafeUtil", true, FieldSerializer.class.getClassLoader()).getField("unsafe").get(null) != null) {
                    z = true;
                }
            } catch (Throwable th) {
                if (Log.TRACE) {
                    Log.trace("kryo", "Unsafe is unavailable.", th);
                }
            }
        }
        unsafe = z;
        HashMap map = new HashMap();
        primitiveWrappers = map;
        map.put(Boolean.TYPE, Boolean.class);
        map.put(Byte.TYPE, Byte.class);
        map.put(Character.TYPE, Character.class);
        map.put(Double.TYPE, Double.class);
        map.put(Float.TYPE, Float.class);
        map.put(Integer.TYPE, Integer.class);
        map.put(Long.TYPE, Long.class);
        map.put(Short.TYPE, Short.class);
    }

    public static boolean isClassAvailable(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Exception unused) {
            Log.debug("kryo", "Class not available: " + str);
            return false;
        }
    }

    public static Class getWrapperClass(Class cls) {
        if (cls == Integer.TYPE) {
            return Integer.class;
        }
        if (cls == Float.TYPE) {
            return Float.class;
        }
        if (cls == Boolean.TYPE) {
            return Boolean.class;
        }
        if (cls == Byte.TYPE) {
            return Byte.class;
        }
        if (cls == Long.TYPE) {
            return Long.class;
        }
        if (cls == Character.TYPE) {
            return Character.class;
        }
        if (cls == Double.TYPE) {
            return Double.class;
        }
        return cls == Short.TYPE ? Short.class : cls;
    }

    public static Class getPrimitiveClass(Class cls) {
        if (cls == Integer.class) {
            return Integer.TYPE;
        }
        if (cls == Float.class) {
            return Float.TYPE;
        }
        if (cls == Boolean.class) {
            return Boolean.TYPE;
        }
        if (cls == Byte.class) {
            return Byte.TYPE;
        }
        if (cls == Long.class) {
            return Long.TYPE;
        }
        if (cls == Character.class) {
            return Character.TYPE;
        }
        if (cls == Double.class) {
            return Double.TYPE;
        }
        if (cls == Short.class) {
            return Short.TYPE;
        }
        return cls == Void.class ? Void.TYPE : cls;
    }

    public static boolean isWrapperClass(Class cls) {
        return cls == Integer.class || cls == Float.class || cls == Boolean.class || cls == Byte.class || cls == Long.class || cls == Character.class || cls == Double.class || cls == Short.class;
    }

    public static boolean isEnum(Class cls) {
        return Enum.class.isAssignableFrom(cls) && cls != Enum.class;
    }

    public static void log(String str, Object obj, int i) {
        if (obj == null) {
            if (Log.TRACE) {
                Log.trace("kryo", str + ": null" + pos(i));
                return;
            }
            return;
        }
        Class<?> cls = obj.getClass();
        if (!cls.isPrimitive() && !isWrapperClass(cls) && cls != String.class) {
            Log.debug("kryo", str + ": " + string(obj) + pos(i));
        } else if (Log.TRACE) {
            Log.trace("kryo", str + ": " + string(obj) + pos(i));
        }
    }

    public static String pos(int i) {
        return i == -1 ? "" : " [" + i + "]";
    }

    public static String string(Object obj) {
        if (obj == null) {
            return "null";
        }
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            return className(cls);
        }
        String strClassName = Log.TRACE ? className(cls) : cls.getSimpleName();
        try {
            if (cls.getMethod("toString", new Class[0]).getDeclaringClass() == Object.class) {
                return strClassName;
            }
        } catch (Exception unused) {
        }
        try {
            String str = String.valueOf(obj) + " (" + strClassName + Constant.AFTER_QUTO;
            return str.length() > 97 ? str.substring(0, 97) + "..." : str;
        } catch (Throwable th) {
            return strClassName + " (toString exception: " + th + Constant.AFTER_QUTO;
        }
    }

    public static String className(Class cls) {
        if (cls == null) {
            return "null";
        }
        if (cls.isArray()) {
            Class elementClass = getElementClass(cls);
            StringBuilder sb = new StringBuilder(16);
            int dimensionCount = getDimensionCount(cls);
            for (int i = 0; i < dimensionCount; i++) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            return className(elementClass) + ((Object) sb);
        }
        if (cls.isPrimitive() || cls == Object.class || cls == Boolean.class || cls == Byte.class || cls == Character.class || cls == Short.class || cls == Integer.class || cls == Long.class || cls == Float.class || cls == Double.class || cls == String.class) {
            return cls.getSimpleName();
        }
        return cls.getName();
    }

    public static String classNames(Class[] clsArr) {
        StringBuilder sb = new StringBuilder(32);
        int length = clsArr.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(className(clsArr[i]));
        }
        return sb.toString();
    }

    public static String canonicalName(Class cls) {
        if (cls == null) {
            return "null";
        }
        String canonicalName = cls.getCanonicalName();
        return canonicalName != null ? canonicalName : className(cls);
    }

    public static String simpleName(Type type) {
        return type instanceof Class ? ((Class) type).getSimpleName() : type.toString();
    }

    public static String simpleName(Class cls, Generics.GenericType genericType) {
        StringBuilder sb = new StringBuilder(32);
        sb.append((cls.isArray() ? getElementClass(cls) : cls).getSimpleName());
        if (genericType.arguments != null) {
            sb.append('<');
            int length = genericType.arguments.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(genericType.arguments[i].toString());
            }
            sb.append('>');
        }
        if (cls.isArray()) {
            int dimensionCount = getDimensionCount(cls);
            for (int i2 = 0; i2 < dimensionCount; i2++) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
        }
        return sb.toString();
    }

    public static int getDimensionCount(Class cls) {
        int i = 0;
        for (Class<?> componentType = cls.getComponentType(); componentType != null; componentType = componentType.getComponentType()) {
            i++;
        }
        return i;
    }

    public static Class getElementClass(Class cls) {
        while (cls.getComponentType() != null) {
            cls = cls.getComponentType();
        }
        return cls;
    }

    public static boolean isAssignableTo(Class<?> cls, Class<?> cls2) {
        if (cls2 == Object.class || cls2.isAssignableFrom(cls)) {
            return true;
        }
        if (cls.isPrimitive()) {
            return isPrimitiveWrapperOf(cls2, cls);
        }
        if (cls2.isPrimitive()) {
            return isPrimitiveWrapperOf(cls, cls2);
        }
        if (cls == ClosureSerializer.Closure.class) {
            return cls2.isInterface();
        }
        return false;
    }

    private static boolean isPrimitiveWrapperOf(Class<?> cls, Class<?> cls2) {
        if (cls2.isPrimitive()) {
            return primitiveWrappers.get(cls2) == cls;
        }
        throw new IllegalArgumentException("First argument has to be primitive type");
    }

    public static boolean isAscii(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) > 127) {
                return false;
            }
        }
        return true;
    }

    public static <T extends SerializerFactory> T newFactory(Class<T> cls, Class<? extends Serializer> cls2) {
        try {
            if (cls2 != null) {
                try {
                    return cls.getConstructor(Class.class).newInstance(cls2);
                } catch (NoSuchMethodException unused) {
                }
            }
            return cls.newInstance();
        } catch (Exception e) {
            if (cls2 == null) {
                throw new IllegalArgumentException("Unable to create serializer factory: " + cls.getName(), e);
            }
            throw new IllegalArgumentException("Unable to create serializer factory \"" + cls.getName() + "\" for serializer class: " + className(cls2), e);
        }
    }
}
