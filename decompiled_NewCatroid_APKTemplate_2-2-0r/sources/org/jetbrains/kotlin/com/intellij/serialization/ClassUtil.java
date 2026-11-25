package org.jetbrains.kotlin.com.intellij.serialization;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/serialization/ClassUtil";
                break;
            case 5:
                objArr[0] = "aClass";
                break;
            case 6:
            case 7:
                objArr[0] = "object";
                break;
            case 8:
                objArr[0] = "value";
                break;
            case 9:
                objArr[0] = "valueClass";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[1] = "typeToClass";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/serialization/ClassUtil";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                objArr[2] = "isPrimitive";
                break;
            case 6:
                objArr[2] = "isMutableCollection";
                break;
            case 7:
                objArr[2] = "isMutableMap";
                break;
            case 8:
            case 9:
                objArr[2] = "stringToEnum";
                break;
            default:
                objArr[2] = "typeToClass";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static Class<?> typeToClass(Type type) {
        if (type == null) {
            $$$reportNull$$$0(0);
        }
        if (type instanceof Class) {
            Class<?> cls = (Class) type;
            if (cls == null) {
                $$$reportNull$$$0(1);
            }
            return cls;
        }
        if (type instanceof TypeVariable) {
            Type rawType = ((TypeVariable) type).getBounds()[0];
            if (!(rawType instanceof Class)) {
                rawType = ((ParameterizedType) rawType).getRawType();
            }
            Class<?> cls2 = (Class) rawType;
            if (cls2 == null) {
                $$$reportNull$$$0(2);
            }
            return cls2;
        }
        if (type instanceof WildcardType) {
            Class<?> cls3 = (Class) ((WildcardType) type).getUpperBounds()[0];
            if (cls3 == null) {
                $$$reportNull$$$0(3);
            }
            return cls3;
        }
        Class<?> cls4 = (Class) ((ParameterizedType) type).getRawType();
        if (cls4 == null) {
            $$$reportNull$$$0(4);
        }
        return cls4;
    }

    public static boolean isPrimitive(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        return cls.isPrimitive() || cls == String.class || cls == Integer.class || cls == Long.class || cls == Boolean.class || cls == Double.class || cls == Float.class || cls.isEnum() || Date.class.isAssignableFrom(cls);
    }

    public static boolean isMutableCollection(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(6);
        }
        if (obj == Collections.emptyList() || obj == Collections.emptySet() || !(obj instanceof Collection)) {
            return false;
        }
        Class<?> cls = obj.getClass();
        String simpleName = cls.getSimpleName();
        if (simpleName.equals("EmptyList") || simpleName.startsWith("Unmodifiable") || simpleName.equals("EmptySet")) {
            return false;
        }
        String name2 = cls.getName();
        if (name2.equals("java.util.Arrays$ArrayList") || name2.equals("java.util.Collections$SingletonList") || name2.equals("java.util.Collections$SingletonSet")) {
            return false;
        }
        Class<?> declaringClass = cls.getDeclaringClass();
        return declaringClass == null || !"ImmutableCollections".equals(declaringClass.getSimpleName());
    }

    public static boolean isMutableMap(Map<?, ?> map) {
        if (map == null) {
            $$$reportNull$$$0(7);
        }
        if (map == Collections.emptyMap()) {
            return false;
        }
        String simpleName = map.getClass().getSimpleName();
        return (simpleName.equals("EmptyMap") || simpleName.equals("UnmodifiableMap")) ? false : true;
    }

    public static Object stringToEnum(String str, Class<? extends Enum<?>> cls, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (cls == null) {
            $$$reportNull$$$0(9);
        }
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        if (!z) {
            for (Enum r2 : enumArr) {
                if (r2.toString().equals(str)) {
                    return r2;
                }
            }
        }
        for (Enum r1 : enumArr) {
            if (r1.toString().equalsIgnoreCase(str)) {
                return r1;
            }
        }
        return null;
    }
}
