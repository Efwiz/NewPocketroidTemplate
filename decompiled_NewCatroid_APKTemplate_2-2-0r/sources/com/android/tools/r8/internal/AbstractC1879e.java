package com.android.tools.r8.internal;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1879e {
    static final Type[] a = new Type[0];

    public static Type a(Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            return cls.isArray() ? new C1655b(a(cls.getComponentType())) : cls;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new C1729c(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            return new C1655b(((GenericArrayType) type).getGenericComponentType());
        }
        if (!(type instanceof WildcardType)) {
            return type;
        }
        WildcardType wildcardType = (WildcardType) type;
        return new C1806d(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
    }

    public static Type[] b(Type type, Class cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        AbstractC1575a.a(Map.class.isAssignableFrom(cls));
        Type typeA = a(type, cls, a(type, cls, Map.class));
        return typeA instanceof ParameterizedType ? ((ParameterizedType) typeA).getActualTypeArguments() : new Type[]{Object.class, Object.class};
    }

    public static Class c(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            AbstractC1575a.a(rawType instanceof Class);
            return (Class) rawType;
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance((Class<?>) c(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return c(((WildcardType) type).getUpperBounds()[0]);
        }
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? "null" : type.getClass().getName()));
    }

    public static String d(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    static void b(Type type) {
        AbstractC1575a.a(((type instanceof Class) && ((Class) type).isPrimitive()) ? false : true);
    }

    public static boolean a(Type type, Type type2) {
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedType2 = (ParameterizedType) type2;
            Type ownerType = parameterizedType.getOwnerType();
            Type ownerType2 = parameterizedType2.getOwnerType();
            return (ownerType == ownerType2 || (ownerType != null && ownerType.equals(ownerType2))) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            if (type2 instanceof GenericArrayType) {
                return a(((GenericArrayType) type).getGenericComponentType(), ((GenericArrayType) type2).getGenericComponentType());
            }
            return false;
        }
        if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            WildcardType wildcardType2 = (WildcardType) type2;
            return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
        }
        if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
            return false;
        }
        TypeVariable typeVariable = (TypeVariable) type;
        TypeVariable typeVariable2 = (TypeVariable) type2;
        return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
    }

    static Type a(Type type, Class cls, Class cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class<?>[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                Class<?> cls3 = interfaces[i];
                if (cls3 == cls2) {
                    return cls.getGenericInterfaces()[i];
                }
                if (cls2.isAssignableFrom(cls3)) {
                    return a(cls.getGenericInterfaces()[i], (Class) interfaces[i], cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            while (cls != Object.class) {
                Class<?> superclass = cls.getSuperclass();
                if (superclass == cls2) {
                    return cls.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return a(cls.getGenericSuperclass(), (Class) superclass, cls2);
                }
                cls = superclass;
            }
        }
        return cls2;
    }

    public static Type a(Type type, Class cls) {
        AbstractC1575a.a(Collection.class.isAssignableFrom(cls));
        Type typeA = a(type, cls, a(type, cls, Collection.class));
        if (typeA instanceof WildcardType) {
            typeA = ((WildcardType) typeA).getUpperBounds()[0];
        }
        if (typeA instanceof ParameterizedType) {
            return ((ParameterizedType) typeA).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003f  */
    public static Type a(Type type, Class cls, Type type2) {
        Type type3;
        WildcardType wildcardType;
        Type typeA;
        Type type4;
        Type type5 = type2;
        while (true) {
            int i = 0;
            if (type5 instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) type5;
                GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
                Class cls2 = genericDeclaration instanceof Class ? (Class) genericDeclaration : null;
                if (cls2 == null) {
                    type4 = typeVariable;
                } else {
                    Type typeA2 = a(type, cls, cls2);
                    if (typeA2 instanceof ParameterizedType) {
                        TypeVariable[] typeParameters = cls2.getTypeParameters();
                        while (true) {
                            if (i < typeParameters.length) {
                                if (typeVariable.equals(typeParameters[i])) {
                                    type4 = ((ParameterizedType) typeA2).getActualTypeArguments()[i];
                                    break;
                                }
                                i++;
                            } else {
                                throw new NoSuchElementException();
                            }
                        }
                    } else {
                        type4 = typeVariable;
                    }
                }
                if (type4 == typeVariable) {
                    return type4;
                }
                type5 = type4;
            } else {
                if (type5 instanceof Class) {
                    Class cls3 = (Class) type5;
                    if (cls3.isArray()) {
                        Class<?> componentType = cls3.getComponentType();
                        Type typeA3 = a(type, cls, (Type) componentType);
                        return componentType == typeA3 ? cls3 : new C1655b(typeA3);
                    }
                }
                if (type5 instanceof GenericArrayType) {
                    GenericArrayType genericArrayType = (GenericArrayType) type5;
                    Type genericComponentType = genericArrayType.getGenericComponentType();
                    Type typeA4 = a(type, cls, genericComponentType);
                    return genericComponentType == typeA4 ? genericArrayType : new C1655b(typeA4);
                }
                if (type5 instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type5;
                    Type ownerType = parameterizedType.getOwnerType();
                    Type typeA5 = a(type, cls, ownerType);
                    boolean z = typeA5 != ownerType;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    int length = actualTypeArguments.length;
                    while (i < length) {
                        Type typeA6 = a(type, cls, actualTypeArguments[i]);
                        if (typeA6 != actualTypeArguments[i]) {
                            if (!z) {
                                actualTypeArguments = (Type[]) actualTypeArguments.clone();
                                z = true;
                            }
                            actualTypeArguments[i] = typeA6;
                        }
                        i++;
                    }
                    return z ? new C1729c(typeA5, parameterizedType.getRawType(), actualTypeArguments) : parameterizedType;
                }
                if (type5 instanceof WildcardType) {
                    wildcardType = (WildcardType) type5;
                    Type[] lowerBounds = wildcardType.getLowerBounds();
                    Type[] upperBounds = wildcardType.getUpperBounds();
                    if (lowerBounds.length == 1) {
                        Type typeA7 = a(type, cls, lowerBounds[0]);
                        if (typeA7 != lowerBounds[0]) {
                            type3 = type5;
                            type3 = wildcardType;
                            return new C1806d(new Type[]{Object.class}, new Type[]{typeA7});
                        }
                    } else if (upperBounds.length == 1 && (typeA = a(type, cls, upperBounds[0])) != upperBounds[0]) {
                        type3 = type5;
                        type3 = wildcardType;
                        type3 = wildcardType;
                        return new C1806d(new Type[]{typeA}, a);
                    }
                }
                type3 = type5;
                type3 = wildcardType;
                type3 = wildcardType;
                type3 = type5;
                type3 = wildcardType;
                type3 = type5;
                type3 = wildcardType;
                type3 = type5;
                return type3;
            }
        }
    }
}
